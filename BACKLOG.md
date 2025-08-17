# 📋 Project Backlog – Spring MCP Training

This backlog tracks the key features and tasks for implementing JWT authentication in the project.  
Statuses follow the legend below and are updated as development progresses.  
Use Spring MCP Client to perform connexion with MCP Server from express.

---

## 📌 Legend – Task Status

- ✅ **Done** – Task completed
- 🚧 **In Progress** – Task actively being worked on
- 🕓 **To Do** – Task not yet started
- 🔥 **High Priority** – Critical feature or urgent fix

---

## 🚀 Epic 1 – Authentication

| Task                    | Priority | Status | Assignee    | Due Date   | Notes                                                            |
| ----------------------- | -------- | ------ | ----------- | ---------- | ---------------------------------------------------------------- |
| Authentication with JWT | 🔥       | ✅     | _Hasintsoa_ | 2025-08-10 | Implement signing with `KeyPair`                                 |
| Simple Refresh Token    | 🔥       | ✅     | _Hasintsoa_ | 2025-08-11 | Store refresh token in database                                  |
| Revoke Token            | 🔥       | ✅     | _Hasintsoa_ | 2025-08-12 | Mark revoked tokens in database when `logout`                    |
| Login Attempts Tracking | 🔥       | ✅     | _Hasintsoa_ | 2025-08-13 | Use Redis to store & check login attempts and `lock` user        |
| 2FA Authentication      | 🔥       | ✅     | _Hasintsao_ | 2025-08-17 | Send Mail to verify user's account AND enable user               |
| Password Recovery       | 🔥       | 🕓     | _Hasintsoa_ | 2025-08-23 | Send Mail to reset user's password (no bundle)                   |
| OAuth2 Social Login     | 🔥       | 🕓     | _Hasintsoa_ | 2025-08-23 | Use `Google` and `Github` to perform Social login Authentication |

---

## 🚀 Epic 2 – MCP Client

| Task | Priority | Status | Assignee | Due Date | Notes |
| ---- | -------- | ------ | -------- | -------- | ----- |
|      |          |        |          |          |       |

---

## 🗒 Notes

- **Epic 1** covers the full authentication lifecycle: login, token issuance, refresh, and security checks.
- Redis integration is used for tracking login attempts to mitigate brute-force attacks.
- Future work may include **multi-factor authentication (MFA)** and **OAuth2 social logins**.
