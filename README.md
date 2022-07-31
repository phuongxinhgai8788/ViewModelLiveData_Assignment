# ViewModelLiveData_Assignment
Dear anh Đức, 
Em gửi anh bài ViewModelLiveData, em xin mô tả về bài này:
Khi activity được start, nó sẽ điều hướng đến màn hình ListFragment.
Trên AppBar có một menu icon, anh click vào đó tự động một item trống 
sẽ được add vào database. Khi click vào item, màn hình DetailFragment 
hiện ra chứa thông tin của item có thể edit được. Sau khi điền đầy đủ thông
tin, khi click vào Save button thì thông tin sẽ được lưu lại và hiển thị
trên một item của list, click vào Cancel button thì item mới được thêm sẽ
bị xóa.
Em đã áp dụng ViewModel, LiveData, two-way databinding, share viewmodel giữa hai 
fragments. Nhưng có một số sự kiện ở các button nó reference đến View, Activity nên
em vẫn chưa tìm cách để cho nó vào ViewModel để bind vào file layout được
Em rất mong phản hồi từ anh ạ!
Em cảm ơn anh
Lê Thị Minh Phượng
