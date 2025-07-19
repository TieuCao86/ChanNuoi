// Lấy mã chuồng từ URL (?maChuong=...)
const urlParams = new URLSearchParams(window.location.search);
const maChuong = urlParams.get("maChuong");

// Hiển thị dữ liệu chuồng nuôi vào giao diện
function hienThiChiTietChuong(data) {
    document.getElementById("tenChuong").innerText = data.tenChuong || "---";
    document.getElementById("maChuongVaKhu").innerText = `Mã chuồng: ${data.maChuong || "---"} • Khu: ${data.tenKhu || "---"}`;
    document.getElementById("trangThai").innerText = data.trangThai || "---";
    document.getElementById("soLuong").innerText = `${data.soLuongVatNuoi ?? 0} con`;
    document.getElementById("loaiVatNuoi").innerText = data.loaiVatNuoi || "---";
    document.getElementById("ngayBatDau").innerText = data.ngayBatDau || "---";
    document.getElementById("soLuongVatNuoi").innerText = `${data.soLuongVatNuoi ?? 0} con`;

    // Nhân viên
    const nvList = document.getElementById("nhanVienPhuTrach");
    nvList.innerHTML = "";
    if (Array.isArray(data.nhanVienPhuTrach) && data.nhanVienPhuTrach.length > 0) {
        data.nhanVienPhuTrach.forEach(nv => {
            const li = document.createElement("li");
            li.className = "list-group-item";
            li.innerText = nv;
            nvList.appendChild(li);
        });
    } else {
        const li = document.createElement("li");
        li.className = "list-group-item text-muted";
        li.innerText = "Chưa có người phụ trách";
        nvList.appendChild(li);
    }

    // Tiến độ
    const tienDo = typeof data.tienDo === "number" ? data.tienDo : 0;
    document.getElementById("tienDoText").innerText = `${tienDo}%`;
    document.getElementById("tienDoBar").style.width = `${tienDo}%`;
    document.getElementById("tienDoBar").innerText = `${tienDo}%`;

    // Chi phí
    const formatter = new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" });
    const chiPhi = typeof data.chiPhiThang === "number" ? data.chiPhiThang : 0;
    document.getElementById("chiPhiThang").innerText = formatter.format(chiPhi);
}

// Gọi API lấy chi tiết chuồng
function loadChuongDetail() {
    if (!maChuong) {
        return;
    }

    fetch(`${apiChuongNuoi}/${maChuong}`)
        .then(res => {
            if (!res.ok) throw new Error("Không tìm thấy chuồng nuôi");
            return res.json();
        })
        .then(data => {
            if (!data || !data.maChuong) {
                return;
            }
            hienThiChiTietChuong(data);
        })
        .catch(err => {
            console.error("Lỗi khi tải chi tiết chuồng:", err);
        });
}

// Khi trang đã tải xong
document.addEventListener("DOMContentLoaded", () => {
    loadChuongDetail();

    if (window.lucide && typeof lucide.createIcons === "function") {
        lucide.createIcons();
    }
});
