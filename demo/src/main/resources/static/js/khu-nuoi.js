const API_KHUNUOI = "/api/khu-nuoi";
const KHU_COLUMNS = [
    { key: 'maKhu', label: 'Mã khu' },
    { key: 'tenKhu', label: 'Tên khu' },
    { key: 'moTa', label: 'Mô tả' },
    { key: 'trangThai', label: 'Trạng thái' },
    {
        key: 'ngayTao',
        label: 'Ngày tạo',
        formatter: (value) => new Date(value).toLocaleDateString('vi-VN')
    },
    {
        type: 'actions',
        actions: [
            { label: 'Sửa', className: 'btn-warning', onClick: (item) => editKhu(item.maKhu) },
            { label: 'Xóa', className: 'btn-danger', onClick: (item) => deleteKhu(item.maKhu) }
        ]
    }
];

const KHU_BODY_ID = "khu-nuoi-body";
const KHU_ROW_CLICK_URL = (item) =>
    `/chuong-nuoi?maKhu=${encodeURIComponent(item.maKhu)}&tenKhu=${encodeURIComponent(item.tenKhu)}`;

// KHỞI TẠO DỮ LIỆU BẢNG
function initKhuNuoi() {
    renderTable({
        url: API_KHUNUOI,
        bodyElementId: KHU_BODY_ID,
        columns: KHU_COLUMNS,
        rowClickUrlBuilder: KHU_ROW_CLICK_URL
    });
}

// HÀM XOÁ
function deleteKhu(maKhu) {
    if (confirm("Bạn có chắc muốn xoá khu này không?")) {
        fetch(`${API_KHUNUOI}/${maKhu}`, {
            method: 'DELETE'
        }).then(() => {
            initKhuNuoi();
            resetForm();
        });
    }
}

// RESET FORM
function resetForm() {
    document.getElementById("khu-nuoi-form").reset();
    document.getElementById("maKhu").value = "";
}

// SUBMIT FORM
const form = document.getElementById("khu-nuoi-form");
if (form) {
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        const maKhu = document.getElementById("maKhu").value.trim();
        const data = {
            tenKhu: document.getElementById("tenKhu").value.trim(),
            moTa: document.getElementById("moTa").value.trim(),
            trangThai: document.getElementById("trangThai").value.trim()
        };

        const method = maKhu ? "PUT" : "POST";
        const url = maKhu ? `${API_KHUNUOI}/${maKhu}` : API_KHUNUOI;

        fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(() => {
            initKhuNuoi();
            resetForm();
        });
    });
}

// GỌI KHI TẢI TRANG
initKhuNuoi();
