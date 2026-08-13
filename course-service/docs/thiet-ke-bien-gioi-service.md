# Thiết kế biên giới Service

## 1. Danh sách Service

| Service | Cổng | Database | Trách nhiệm |
|---------|------|----------|-------------|
| api-gateway | 8080 | Không có | Điểm vào duy nhất, định tuyến, xác thực sơ bộ, CORS |
| auth-service | 8081 | auth_db | Quản lý User, Student, đăng nhập, xác thực JWT |
| course-service | 8082 | course_db | Quản lý môn học, tìm kiếm, phân trang, quản lý số chỗ |
| registration-service | 8083 | registration_db | Quản lý đăng ký học phần |



## 2. Nguyên tắc sở hữu dữ liệu

- Mỗi service có database riêng.
- Không service nào được truy cập trực tiếp database của service khác.
- Muốn lấy dữ liệu phải gọi REST API.
- registration-service chỉ lưu courseId, không lưu bảng Course.



## 3. Gateway Routing

| Route | Forward tới |
|--------|-------------|
| /api/auth/** | http://localhost:8081 |
| /api/courses/** | http://localhost:8082 |
| /api/registrations/** | http://localhost:8083 |
| /api/public/courses | http://localhost:8082 |