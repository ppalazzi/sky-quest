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

## 🚀 Future Improvements

- Add refresh token flow for long-lived sessions.
- Add automatic logout on token expiry (middleware + 401 handling).
- Add role-based access control (RBAC) at route level.
- Add `/api/me` endpoint to verify and return the current user session.

---

## 📁 Related Files

| File                      | Purpose                                     |
|---------------------------|---------------------------------------------|
| `/app/api/login/route.ts` | Handles login and forwards `Set-Cookie`     |
| `/app/api/dashboard/...`  | Protected routes that use `apiServer`       |
| `/service/apiClient.ts`   | Axios for client-side components            |
| `/service/apiServer.ts`   | Axios for server-side routes or components  |

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
