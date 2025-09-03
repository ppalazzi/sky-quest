# JWT Authentication Architecture in Next.js + Spring Boot

This document describes the authentication strategy implemented in this project using JWT tokens stored in secure HTTP-only cookies. The architecture leverages Next.js (App Router) on the frontend and Spring Boot on the backend. The goal is to provide a secure and maintainable approach to handle authentication in full-stack applications.

---

## 🔐 Overview

- Authentication is handled via JWTs stored in **HTTP-only cookies**.
- Cookies are **set by the Spring Boot backend** upon successful login.
- **Next.js API routes** act as secure server-side proxies to backend services.
- On the server (in `route.ts` or Server Components), JWT cookies are read and forwarded manually to the backend.
- On the client, `withCredentials: true` is used to forward cookies automatically.

---

## ✅ Key Components and Responsibilities

### 1. **Login Flow**

- A client component (`Login.tsx`) sends a `POST /api/login` request with credentials.
- The API route (`/api/login/route.ts`) forwards the credentials to the Spring Boot backend (`/user`).
- If valid, Spring Boot returns a response with a `Set-Cookie: jwt=...` header.
- The API route **forwards the Set-Cookie** back to the browser using `NextResponse.headers.set('set-cookie', ...)`.

### 2. **Cookie Storage**

- The JWT is stored as a secure, HTTP-only cookie:
  - `HttpOnly`: not accessible from JavaScript
  - `Secure`: only sent over HTTPS (in production)
  - `SameSite=Strict`: prevents CSRF
  - `Path=/`: available on all routes

### 3. **Route Handlers for API Calls**

- All frontend API calls are made to `/api/...` routes inside Next.js.
- These `route.ts` handlers read the `jwt` cookie using `cookies()` and **manually forward it** to the Spring backend:

```ts
const token = cookies().get('jwt')?.value;
const res = await apiServer.get('/catalog/messier', {
  headers: {
    Cookie: `jwt=${token}`,
  },
});
```

- This ensures JWT is available even during SSR or server-only logic.

### 4. **Axios Instances**

- **`apiClient.ts`**: used by client-side components
- **`apiServer.ts`**: used by route handlers and server components

```ts
// apiServer.ts example
if (token) {
  config.headers['Cookie'] = `jwt=${token}`;
}
```

This structure separates concerns clearly and prevents server-only imports (like `next/headers`) from breaking client-side code.

### 5. **Server Components**

Server components (like `MessierPage.tsx`) can call backend APIs using `apiServer` directly:

```ts
const token = cookies().get('jwt')?.value;
const res = await apiServer.get('/catalog/messier', {
  headers: {
    Cookie: `jwt=${token}`,
  },
});
```

This enables SSR and avoids exposing tokens to the client.

---

## ⚖️ Advantages

- 🔐 **Secure**: Tokens are not accessible via JavaScript (HttpOnly)
- 🔄 **Reusable**: Centralized Axios configuration for client and server
- 📦 **Encapsulated**: Spring Boot handles token issuing and validation
- 📤 **SSR-Friendly**: Token is available in route handlers and Server Components
- 🚫 **XSS-Safe**: No tokens stored in localStorage or exposed in JS

---

## ⚠️ Considerations / Limitations

- Middleware can't inject cookies into outgoing requests — this must be handled manually.
- JWTs must be validated in every request on the backend.
- Refresh token strategy (if needed) must be implemented separately.
- Cookies must be forwarded explicitly in `Set-Cookie` from Next to browser.

---

## 🛡 Best Practices

- Always check for the presence of the token before making protected API calls.
- Use `withCredentials: true` in the client for requests to `/api/*`.
- Redact sensitive tokens from logs (`console.log(token)` in production = ❌).
- Protect your `/api` routes with middleware if needed for additional checks.

---

## ♻️ Session Hydration on Refresh (Implemented)

A common issue with client-side state (e.g., Zustand) is losing user info after a page refresh. This project addresses it with a backend endpoint and a lightweight hydration hook.

### Backend: `GET /user/me`
- Path: `/user/me` in `UserController`.
- Reads the `jwt` cookie from the incoming request.
- Delegates to `UserService.findUserByToken(token)` to resolve the current user.
- Returns `200 OK` with the `UserDTO` if valid; otherwise `401 Unauthorized`.

### Frontend: `/api/me` route and Auth hydrator
- Next.js route handler: `/app/api/me/route.ts` proxies to Spring `/user/me`.
  - Reads/forwards the `jwt` cookie server-side via the existing Axios interceptor in `service/api.ts`.
  - Returns 200 with `user` on success, or proper status codes (401 on unauthorized).
- Zustand store: `src/store/useAuth.ts`
  - Adds `init()` that calls `/api/me` to restore `user` and `isAuthenticated`.
  - Tracks `isHydrated` and `isHydrating` to avoid duplicate calls and flickers.
- Client hydrator: `src/app/AuthHydrator.tsx`
  - Calls `useAuth().init()` once on app mount.
  - Included in `src/app/layout.tsx` so it runs globally.

### Logout
- `POST /user/logout` clears the `jwt` cookie (backend).
- Frontend `/api/logout` mirrors cookie deletion back to browser, then redirects to `/login`.
- Zustand `logout()` clears in-memory state.

### Request flow (after refresh)
1. Browser loads a page; `AuthHydrator` triggers `useAuth.init()`.
2. `init()` calls Next.js `/api/me`.
3. `/api/me` forwards the `jwt` cookie to Spring `/user/me`.
4. If valid, Spring returns the user; Zustand sets `user` and `isAuthenticated=true`.
5. If invalid/expired, Zustand sets `user=null` and `isAuthenticated=false`.

---

## 🚀 Future Improvements

- Add refresh token flow for long-lived sessions.
- Add automatic logout on token expiry (middleware + 401 handling).
- Add role-based access control (RBAC) at route level.

---

## 📁 Related Files

| File                         | Purpose                                        |
|------------------------------|------------------------------------------------|
| `/app/api/login/route.ts`    | Handles login and forwards `Set-Cookie`        |
| `/app/api/logout/route.ts`   | Logs out and clears cookie                     |
| `/app/api/me/route.ts`       | Returns current user based on cookie           |
| `/app/AuthHydrator.tsx`      | Triggers Zustand hydration on app mount        |
| `/app/layout.tsx`            | Includes the hydrator                          |
| `/store/useAuth.ts`          | Zustand store with `init` + hydration flags    |
| `/service/apiClient.ts`      | Axios for client-side components               |
| `/service/api.ts`            | Axios for server-side routes or components     |

---

## 👥 Contributing

If you're adding new protected API routes, always:

1. Use `apiServer` if it's server-side.
2. Forward the `jwt` cookie manually.
3. Return a clear 401 if token is missing or invalid.

---

This architecture ensures a strong separation of concerns, secure token handling, and a developer-friendly experience.

> For questions or improvements, please open an issue or contact the maintainers.

---

Happy coding! 🚀
