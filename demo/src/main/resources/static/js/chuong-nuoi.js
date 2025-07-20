const apiChuongNuoi = "/api/chuong-nuoi";

// Hàm render bảng chuồng
function renderChuongTable(data, bodyElement) {
    bodyElement.innerHTML = '';

    if (!data || data.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.colSpan = 7;
        cell.className = "text-center text-muted";
        cell.textContent = "Không có dữ liệu chuồng nuôi.";
        row.appendChild(cell);
        bodyElement.appendChild(row);
        return;
    }

    data.forEach(cn => {
        const row = document.createElement('tr');

        const tdMa = document.createElement('td');
        tdMa.textContent = cn.maChuong;
        row.appendChild(tdMa);

        const tdTen = document.createElement('td');
        tdTen.textContent = cn.tenChuong;
        row.appendChild(tdTen);

        const tdLoai = document.createElement('td');
        tdLoai.textContent = cn.loaiVatNuoi;
        row.appendChild(tdLoai);

        const tdSucChua = document.createElement('td');
        tdSucChua.textContent = cn.sucChua;
        row.appendChild(tdSucChua);

        const tdTrangThai = document.createElement('td');
        tdTrangThai.textContent = cn.trangThai;
        row.appendChild(tdTrangThai);

        const tdNgayTao = document.createElement('td');
        tdNgayTao.textContent = new Date(cn.ngayTao).toLocaleDateString('vi-VN');
        row.appendChild(tdNgayTao);

        const tdActions = document.createElement('td');

        const btnSua = document.createElement('button');
        btnSua.className = 'btn btn-warning btn-sm me-1';
        btnSua.textContent = 'Sửa';
        btnSua.addEventListener('click', (e) => {
            e.stopPropagation();
            editChuong(cn.maChuong);
        });

        const btnXoa = document.createElement('button');
        btnXoa.className = 'btn btn-danger btn-sm';
        btnXoa.textContent = 'Xóa';
        btnXoa.addEventListener('click', (e) => {
            e.stopPropagation();
            deleteChuong(cn.maChuong);
        });

        tdActions.appendChild(btnSua);
        tdActions.appendChild(btnXoa);
        row.appendChild(tdActions);

        row.style.cursor = 'pointer';
        row.addEventListener('click', () => {
            const url = `/chuong-nuoi/detail?maChuong=${encodeURIComponent(cn.maChuong)}`;
            loadContent(url);
            window.history.pushState({}, '', url);
        });

        bodyElement.appendChild(row);
    });
}

// Load toàn bộ chuồng
function initChuongNuoi() {
    fetch(apiChuongNuoi)
        .then(res => res.json())
        .then(data => {
            const body = document.getElementById('chuong-nuoi-body');
            if (body) renderChuongTable(data, body);
        })
        .catch(err => console.error("Lỗi khi tải chuồng:", err));
}

// Load chuồng theo khu
function loadChuongNuoiTheoKhu(maKhu, tenKhu) {
    const apiByKhu = `${apiChuongNuoi}/by-khu/${maKhu}`;
    const title = document.getElementById("chuong-title");
    const body = document.getElementById("chuong-nuoi-body");

    if (title) title.textContent = `Danh sách chuồng thuộc khu: ${tenKhu}`;
    if (!body) return;

    fetch(apiByKhu)
        .then(res => res.json())
        .then(data => renderChuongTable(data, body))
        .catch(err => {
            console.error("Lỗi khi tải chuồng theo khu:", err);
            body.innerHTML = `<tr><td colspan="7" class="text-danger text-center">Lỗi khi tải dữ liệu</td></tr>`;
        });
}

// Lấy chi tiết chuồng để sửa
function editChuong(maChuong) {
    fetch(`${apiChuongNuoi}/${maChuong}`)
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
        fetch(`${apiChuongNuoi}/${maChuong}`, {
            method: 'DELETE'
        }).then(() => {
            initChuongNuoi();
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

// Hiện/Ẩn form
function showChuongForm() {
    document.getElementById("chuong-nuoi-form").style.display = 'block';
}
function hideChuongForm() {
    resetChuongForm();
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
        const url = maChuong ? `${apiChuongNuoi}/${maChuong}` : apiChuongNuoi;

        fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(() => {
            initChuongNuoi();
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

    console.log("➡️ Hàm applyFilters đang chạy");

    // URL nội bộ để thay đổi thanh địa chỉ
    const fullUrl = '/chuong-nuoi?' + params.toString();
    window.history.replaceState(null, '', fullUrl);

    // URL thật gọi API
    const apiUrl = apiChuongNuoi + '/filter?' + params.toString();
    console.log("📡 Gọi API:", apiUrl);

    console.log("➡️ Hàm applyFilters đang chạy", {
        maChuong, tenChuong, loaiVatNuoi, sucChua, trangThai, ngayTao
    });

    fetch(apiUrl)
        .then(res => {
            if (!res.ok) throw new Error("Lỗi khi gọi API lọc");
            return res.json();
        })
        .then(data => {
            console.log("✅ Dữ liệu trả về:", data);
            const body = document.getElementById("chuong-nuoi-body");
            renderChuongTable(data, body);
        })
        .catch(err => {
            console.error("❌ Lỗi khi gọi API lọc:", err);
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

    if (maKhu && tenKhu) {
        loadChuongNuoiTheoKhu(maKhu, tenKhu);
    } else {
        initChuongNuoi();
    }

    // ✅ Gọi initFilters để gắn sự kiện lọc
    initFilters();
});


