# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

SkyQuest is a full-stack web app for astronomers to track celestial objects (Messier catalog) and log observations. It is a Maven multi-module monorepo with two modules declared in the root `pom.xml`:

- **`back-sky-quest/`** — Spring Boot 3.4 / Java 21 REST API (`com.palazzisoft.skyquest`)
- **`front-sky-quest/`** — Next.js 15 (App Router) + TypeScript + React 18 frontend

The root `pom.xml` also builds `front-sky-quest` as a Maven module, but day-to-day frontend work uses npm directly (see below).

## Commands

### Backend (`back-sky-quest/`)
```bash
./mvnw spring-boot:run                              # run API on :8080
./mvnw test                                         # run all tests
./mvnw test -Dtest=UserControllerTest               # single test class
./mvnw test -Dtest=UserControllerTest#methodName    # single test method
./mvnw clean package                                # build jar
```
Requires a running PostgreSQL (see docker-compose). The Maven wrapper (`mvnw`) lives in `back-sky-quest/`, not the repo root.

### Frontend (`front-sky-quest/`)
```bash
npm run dev            # dev server on :3000 (Turbopack)
npm run build          # production build
npm run lint           # ESLint (next lint)
npm run fix            # eslint --fix on src/
npm run format         # prettier --write
npm run format:check   # prettier --check
```
No test runner is configured for the frontend.

### Full stack via Docker
```bash
docker compose up --build   # postgres :5432, backend :8080, frontend :3000
```

## Architecture

### Authentication (the core cross-cutting concern)
JWT stored in an **HTTP-only cookie named `jwt`**. Full details in `documentation/JWT_Auth_Architecture.md`. The flow spans both modules:

1. Login: browser → Next.js `/api/login` route handler → Spring `POST /user`. Spring validates credentials, generates the JWT (`AuthenticationService`), and returns it via a `Set-Cookie` header. The Next.js route **manually forwards that `Set-Cookie`** back to the browser.
2. Authenticated requests: Spring's `JwtAuthenticationFilter` reads the `jwt` cookie (not an `Authorization` header) and populates the `SecurityContext`. In `SecurityConfig`, `/user/**` and `/actuator/**` are public; everything else requires auth.
3. Session hydration on refresh: `AuthHydrator` (mounted in `layout.tsx`) calls `useAuth().init()` → Next.js `/api/me` → Spring `GET /user/me`, restoring the Zustand store. Guarded by `isHydrated`/`isHydrating` flags in `src/store/useAuth.ts`.

Because Next.js middleware cannot inject cookies into outgoing requests, cookie forwarding is done manually in route handlers / the `api.ts` interceptor.

### Frontend structure
- **Two Axios instances, do not mix them:**
  - `src/service/apiClient.ts` — client-side; `baseURL = NEXT_PUBLIC_CLIENT_URL` (the Next.js app itself). Client code calls Next.js `/api/*` routes, never the Spring backend directly.
  - `src/service/api.ts` — server-side (route handlers, Server Components); `baseURL = NEXT_PUBLIC_BACKEND_URL` (Spring). Its request interceptor reads the `jwt` cookie via `next/headers` and forwards it. Importing this in client code will break the build.
- `src/app/api/*/route.ts` are the **BFF proxy layer** between the browser and Spring — every backend call should go through here (or a Server Component using `api.ts`).
- State: **Zustand** (`src/store/useAuth.ts`). UI: **shadcn/ui** (New York style) in `src/components/ui/`, configured via `components.json`. Tailwind CSS.
- Route protection: `src/middleware.ts` guards `/dashboard/*` and `/service/*`, redirecting to `/login` when the `jwt` cookie is absent.
- Path aliases (`tsconfig.json`): `@/*` → `src/*`, `@modules/*` → `src/modules/*`.
- Feature UI lives in `src/modules/`; reusable pieces in `src/components/`; server actions in `src/action/`.

### Backend structure
Standard layered Spring Boot: `controller` → `service` → `repository`/`entity`. Notable points:
- `User` (entity) implements Spring Security's `UserDetails` directly; `UserDetailsService` is a lambda in `SecurityConfig` backed by `UserRepository.findByUsername`.
- DTOs are Java records with Lombok `@Builder`; entity↔DTO mapping via ModelMapper (`ModelMapperConfig`).
- Catalog data is **not in the database** — `CatalogService` reads `src/main/resources/data/messier.json` from the classpath and serves it via `GET /catalog/messier`.
- Global error handling in `ControllerExceptionHandler` (`@ControllerAdvice`).
- Passwords hashed with BCrypt.

### Backend endpoints
- `POST /user` — login (returns user + sets `jwt` cookie)
- `POST /user/logout` — clears the `jwt` cookie
- `GET /user/me` — current user from the `jwt` cookie (401 if missing/invalid)
- `GET /catalog/messier` — Messier catalog (auth required)

## Configuration notes
- Backend config in `back-sky-quest/src/main/resources/application.properties`: PostgreSQL datasource, `ddl-auto=update`, and the JWT secret/expiration. DB schema is `skyquest`.
- Frontend env files (`.env.local`, `.env.dev`, `.env.production`) define `NEXT_PUBLIC_BACKEND_URL` (Spring) and `NEXT_PUBLIC_CLIENT_URL` (Next.js). Keep these two roles distinct — they feed the two different Axios instances.

## Conventions
- Commit messages / branches use the Jira-style prefix `SQ-<n>:` and often tag scope `[FE]` / `[BE]`. The main branch for PRs is `develop`.
