# Blueprint API

## Auth Service

| Method | Endpoint | Mô tả |
|---------|----------|-------|
| POST | /auth/login | Đăng nhập |
| POST | /auth/register | Đăng ký |



## Course Service

| Method | Endpoint | Mô tả |
|---------|----------|-------|
| GET | /courses | Danh sách môn học |
| GET | /courses/{id} | Chi tiết môn học |
| POST | /courses | Thêm môn học |
| PUT | /courses/{id} | Cập nhật môn học |
| DELETE | /courses/{id} | Xóa môn học |

### API nội bộ

| Method | Endpoint |
|---------|----------|
| PATCH | /internal/courses/{id}/reserve-seat |
| PATCH | /internal/courses/{id}/release-seat |


## Registration Service

| Method | Endpoint | Mô tả |
|---------|----------|-------|
| POST | /registrations | Đăng ký học phần |
| GET | /registrations/my | Danh sách đăng ký |
| DELETE | /registrations/{id} | Hủy đăng ký |