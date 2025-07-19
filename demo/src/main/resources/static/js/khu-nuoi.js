const apiKhuNuoi = "/api/khu-nuoi";

function initKhuNuoi() {
    fetch(apiKhuNuoi)
        .then(res => res.json())
        .then(data => {
            const body = document.getElementById('khu-nuoi-body');
            if (!body) return;
            body.innerHTML = '';

            data.forEach(khu => {
                const row = document.createElement('tr');

                // Cột: Mã khu
                const tdMaKhu = document.createElement('td');
                tdMaKhu.textContent = khu.maKhu;
                row.appendChild(tdMaKhu);

                // Cột: Tên khu
                const tdTenKhu = document.createElement('td');
                tdTenKhu.textContent = khu.tenKhu;
                row.appendChild(tdTenKhu);

                // Cột: Mô tả
                const tdMoTa = document.createElement('td');
                tdMoTa.textContent = khu.moTa;
                row.appendChild(tdMoTa);

                // Cột: Trạng thái
                const tdTrangThai = document.createElement('td');
                tdTrangThai.textContent = khu.trangThai;
                row.appendChild(tdTrangThai);

                // Cột: Ngày tạo
                const tdNgayTao = document.createElement('td');
                tdNgayTao.textContent = new Date(khu.ngayTao).toLocaleDateString('vi-VN');
                row.appendChild(tdNgayTao);

                // Cột: Hành động
                const tdActions = document.createElement('td');

                // Nút Sửa
                const btnSua = document.createElement('button');
                btnSua.className = 'btn btn-warning btn-sm me-1';
                btnSua.textContent = 'Sửa';
                btnSua.addEventListener('click', (event) => {
                    event.stopPropagation();
                    editKhu(khu.maKhu);
                });

                // Nút Xóa
                const btnXoa = document.createElement('button');
                btnXoa.className = 'btn btn-danger btn-sm';
                btnXoa.textContent = 'Xóa';
                btnXoa.addEventListener('click', (event) => {
                    event.stopPropagation();
                    deleteKhu(khu.maKhu);
                });

                tdActions.appendChild(btnSua);
                tdActions.appendChild(btnXoa);
                row.appendChild(tdActions);

                // Sự kiện click dòng
                row.style.cursor = 'pointer';
                row.addEventListener('click', () => {
                    const url = `/chuong-nuoi?maKhu=${encodeURIComponent(khu.maKhu)}&tenKhu=${encodeURIComponent(khu.tenKhu)}`;
                    loadContent(url);
                    window.history.pushState({}, '', url);
                });

                // Thêm dòng vào bảng
                body.appendChild(row);
            });
        });
}

function showForm() {
    document.getElementById("khu-nuoi-form").style.display = "block";
}

function hideForm() {
    document.getElementById("khu-nuoi-form").style.display = "none";
    document.getElementById("khu-nuoi-form").reset(); // reset form
    document.getElementById("maKhu").value = ""; // xóa mã khu nếu đang sửa
}

function editKhu(maKhu) {
    fetch(`${apiKhuNuoi}/${maKhu}`)
        .then(res => res.json())
        .then(khu => {
            document.getElementById("maKhu").value = khu.maKhu;
            document.getElementById("tenKhu").value = khu.tenKhu;
            document.getElementById("moTa").value = khu.moTa;
            document.getElementById("trangThai").value = khu.trangThai;
        });
}

function deleteKhu(maKhu) {
    if (confirm("Bạn có chắc muốn xoá khu này không?")) {
        fetch(`${apiKhuNuoi}/${maKhu}`, {
            method: 'DELETE'
        }).then(() => {
            initKhuNuoi();
            resetForm();
        });
    }
}

function resetForm() {
    document.getElementById("khu-nuoi-form").reset();
    document.getElementById("maKhu").value = "";
}

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
        const url = maKhu ? `${apiKhuNuoi}/${maKhu}` : apiKhuNuoi;

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

// Gọi khi tải trang
initKhuNuoi();
