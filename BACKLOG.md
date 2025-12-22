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

| Task                    | Priority | Status | Assignee    | Due Date   | Finish Date | Notes                                                                      |
| ----------------------- |:--------:|:------:|:-----------:| ---------- | ----------- | -------------------------------------------------------------------------- |
| Authentication with JWT | 🔥       | ✅      | _Hasintsoa_ | 2025-08-10 | 2025-08-10  | Implement signing with `KeyPair`                                           |
| Simple Refresh Token    | 🔥       | ✅      | _Hasintsoa_ | 2025-08-11 | 2025-08-11  | Store refresh token in database                                            |
| Revoke Token            | 🔥       | ✅      | _Hasintsoa_ | 2025-08-12 | 2025-08-12  | Mark revoked tokens in database when `logout`                              |
| Login Attempts Tracking | 🔥       | ✅      | _Hasintsoa_ | 2025-08-13 | 2025-08-13  | Use Redis to store & check login attempts and `lock` user after n attempts |
| 2FA Authentication      | 🔥       | ✅      | _Hasintsoa_ | 2025-08-17 | 2025-08-17  | Send Mail to verify user's account AND enable user                         |
| Password Recovery       | 🔥       | ✅      | _Hasintsoa_ | 2025-08-23 | 2025-09-16  | Send Mail to reset user's password (no bundle)                             |
| Update Profile User     | 🔥       | ✅      | Hasintsoa   | 2025-11-03 | 2025-11-02  | Update FullName and phone of the user                                      |
| OAuth2 Social Login     | 🕓       | 🕓     | _Hasintsoa_ |            |             | Use `Google` and `Github` to perform Social login Authentication           |

---

## 🚀 Epic 2 – Blog for Articles

| Task                         | Priority | Status | Assignee    | Due Date | Finish Date | Notes                        |
| ---------------------------- |:--------:|:------:| ----------- | -------- | ----------- | ---------------------------- |
| Create Post                  | 🔥       | ✅      | _Hasintsoa_ |          | 2025-11-03  | Add some tags for each posts |
| Create comment for each Post | 🔥       | ✅      | _Hasintsoa_ |          | 2025-11-03  |                              |
| Create Post Like             | 🔥       | ✅      | _Hasintsoa_ |          | 2025-11-06  |                              |

---

## 🗒 Notes

- **Epic 2 :** Create Blog to the project
