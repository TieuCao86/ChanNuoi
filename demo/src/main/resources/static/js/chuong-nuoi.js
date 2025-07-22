const API_CHUONGNUOI = "/api/chuong-nuoi";
const CHUONG_COLUMNS = [
    { key: 'maChuong', label: 'Mã chuồng' },
    { key: 'tenChuong', label: 'Tên chuồng' },
    { key: 'loaiVatNuoi', label: 'Loài vật nuôi' },
    { key: 'sucChua', label: 'Sức chứa' },
    { key: 'trangThai', label: 'Trạng thái' },
    {
        key: 'ngayTao',
        label: 'Ngày tạo',
        formatter: (value) => new Date(value).toLocaleDateString('vi-VN')
    },
    {
        type: 'actions',
        actions: [
            {
                label: 'Sửa',
                className: 'btn-warning',
                onClick: (item) => editChuong(item.maChuong)
            },
            {
                label: 'Xóa',
                className: 'btn-danger',
                onClick: (item) => deleteChuong(item.maChuong)
            }
        ]
    }
];

const CHUONG_BODY_ID = "chuong-nuoi-body";
const CHUONG_ROW_CLICK_URL = (item) => `/chuong-nuoi/detail?maChuong=${encodeURIComponent(item.maChuong)}`;


// Hàm render bảng chuồng
function initChuong(maKhu = null, tenKhu = null) {
    const title = document.getElementById("chuong-title");
    const url = maKhu ? `${API_CHUONGNUOI}/by-khu/${maKhu}` : API_CHUONGNUOI;

    if (title) {
        title.textContent = maKhu
            ? `Danh sách chuồng thuộc khu: ${tenKhu}`
            : "Danh sách Chuồng Nuôi";
    }

    renderTable({
        url,
        bodyElementId: CHUONG_BODY_ID,
        columns: CHUONG_COLUMNS,
        rowClickUrlBuilder: CHUONG_ROW_CLICK_URL
    });
}

// Lấy chi tiết chuồng để sửa
function editChuong(maChuong) {
    fetch(`${API_CHUONGNUOI}/${maChuong}`)
        .then(res => res.json())
        .then(cn => {
            document.getElementById("maChuong").value = cn.maChuong;
            document.getElementById("tenChuong").value = cn.tenChuong;
            document.getElementById("loaiVatNuoi").value = cn.loaiVatNuoi;
            document.getElementById("sucChua").value = cn.sucChua;
            document.getElementById("trangThai").value = cn.trangThai;
            document.getElementById("chuong-nuoi-form").style.display = 'block';
        });
}

// Xóa chuồng
function deleteChuong(maChuong) {
    if (confirm("Bạn có chắc muốn xoá chuồng này không?")) {
        fetch(`${API_CHUONGNUOI}/${maChuong}`, {
            method: 'DELETE'
        }).then(() => {
            initChuong();
            resetChuongForm();
        });
    }
}

// Reset form
function resetChuongForm() {
    document.getElementById("chuong-nuoi-form").reset();
    document.getElementById("maChuong").value = "";
    document.getElementById("chuong-nuoi-form").style.display = 'none';
}

// Submit form tạo/sửa
const chuongForm = document.getElementById("chuong-nuoi-form");
if (chuongForm) {
    chuongForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const maChuong = document.getElementById("maChuong").value.trim();
        const data = {
            tenChuong: document.getElementById("tenChuong").value.trim(),
            loaiVatNuoi: document.getElementById("loaiVatNuoi").value.trim(),
            sucChua: parseInt(document.getElementById("sucChua").value),
            trangThai: document.getElementById("trangThai").value.trim()
        };

        const method = maChuong ? "PUT" : "POST";
        const url = maChuong ? `${API_CHUONGNUOI}/${maChuong}` : API_CHUONGNUOI;

        fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(() => {
            initChuong();
            resetChuongForm();
        });
    });
}

// Debounce function
function debounce(func, delay) {
    let timeout;
    return function () {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, arguments), delay);
    };
}

// Áp dụng filter tự động
function applyFilters() {
    const maChuong = document.getElementById('filterMaChuong').value;
    const tenChuong = document.getElementById('filterTenChuong').value;
    const loaiVatNuoi = document.getElementById('filterLoaiVatNuoi').value;
    const sucChua = document.getElementById('filterSucChua').value;
    const trangThai = document.getElementById('filterTrangThai').value;
    const ngayTao = document.getElementById('filterNgayTao').value;

    const params = new URLSearchParams();
    if (maChuong) params.append('maChuong', maChuong);
    if (tenChuong) params.append('tenChuong', tenChuong);
    if (loaiVatNuoi) params.append('loaiVatNuoi', loaiVatNuoi);
    if (sucChua) params.append('sucChua', sucChua);
    if (trangThai) params.append('trangThai', trangThai);
    if (ngayTao) params.append('ngayTao', ngayTao);

    window.history.replaceState(null, '', '/chuong-nuoi?' + params.toString());

    const apiUrl = `${API_CHUONGNUOI}/filter?${params.toString()}`;

    renderTable({
        url: apiUrl,
        bodyElementId: CHUONG_BODY_ID,
        columns: CHUONG_COLUMNS,
        rowClickUrlBuilder: CHUONG_ROW_CLICK_URL
    });
}

function initFilters() {
    const filterInputs = [
        'filterMaChuong',
        'filterTenChuong',
        'filterLoaiVatNuoi',
        'filterSucChua',
        'filterTrangThai',
        'filterNgayTao'
    ];

    const debouncedApplyFilters = debounce(applyFilters, 300);

    filterInputs.forEach(id => {
        const input = document.getElementById(id);
        if (input) {
            input.addEventListener("input", debouncedApplyFilters);
            input.addEventListener("change", () => {
                console.log(`🌀 Thay đổi ở filter: #${id}`);
                applyFilters();
            });
        }
    });
}

// Khi trang load
document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const maKhu = urlParams.get("maKhu");
    const tenKhu = urlParams.get("tenKhu");

    // ✅ Gọi hàm gộp duy nhất
    initChuong(maKhu, tenKhu);

    // ✅ Gắn sự kiện lọc
    initFilters();
});



