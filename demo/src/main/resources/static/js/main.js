// Gọi để load nội dung động
function loadContent(url, push = true) {
    const fetchUrl = url.split('?')[0]; // fetch fragment không bao gồm query string

    fetch(fetchUrl)
        .then(response => {
            if (!response.ok) throw new Error('Lỗi khi tải nội dung');
            return response.text();
        })
        .then(html => {
            const main = document.getElementById('main-content');
            main.innerHTML = html;

            // ✅ Cập nhật URL trên trình duyệt nếu cần
            if (push) {
                history.pushState(null, '', url); // Cập nhật URL mà không reload
            }

            // ✅ Load file JS tương ứng và khởi tạo
            if (url.startsWith('/khu-nuoi')) {
                loadScriptAndInit('/js/khu-nuoi.js', 'initKhuNuoi');
            } else if (url.startsWith('/chuong-nuoi')) {
                loadScriptAndInit('/js/chuong-nuoi.js', () => {
                    const urlParams = new URLSearchParams(url.split('?')[1] || '');
                    const maKhu = urlParams.get("maKhu");
                    const tenKhu = urlParams.get("tenKhu");
                    const maChuong = urlParams.get("maChuong");

                    if (url.startsWith('/chuong-nuoi/detail') && typeof loadChuongDetail === 'function') {
                        loadChuongDetail(); // ✅ Gọi khi vào trang chi tiết
                    } else if (maKhu && tenKhu && typeof loadChuongNuoiTheoKhu === 'function') {
                        loadChuongNuoiTheoKhu(maKhu, tenKhu);
                    } else if (typeof initChuongNuoi === 'function') {
                        initChuongNuoi(); // mặc định
                    }
                });
            } else if (url.startsWith('/kho/')) {
                loadScriptAndInit('/js/kho.js', 'initKho');
            }
        })
        .catch(error => {
            document.getElementById('main-content').innerHTML =
                `<div class="alert alert-danger">${error}</div>`;
        });
}

// Toggle submenu bên sidebar
function toggleSubmenu(element) {
    element.classList.toggle('collapsed');
    const submenu = element.nextElementSibling;
    submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
}

// Load script JS rồi gọi hàm khởi tạo
function loadScriptAndInit(src, initCallbackOrFunctionName) {
    const existing = document.querySelector(`script[src="${src}"]`);
    const runInit = () => {
        if (typeof initCallbackOrFunctionName === 'function') {
            initCallbackOrFunctionName(); // dùng callback trực tiếp
        } else if (typeof window[initCallbackOrFunctionName] === 'function') {
            window[initCallbackOrFunctionName](); // dùng tên hàm nếu truyền string
        } else {
            console.warn(`⚠️ Không tìm thấy hàm ${initCallbackOrFunctionName}`);
        }
    };

    if (existing) {
        runInit(); // Nếu script đã tồn tại, chỉ gọi lại init
        return;
    }

    const script = document.createElement('script');
    script.src = src;
    script.defer = true;
    script.onload = runInit;
    script.onerror = () => {
        console.error(`❌ Không thể tải script ${src}`);
    };
    document.body.appendChild(script);
}
window.addEventListener('popstate', () => {
    const currentUrl = window.location.pathname + window.location.search;
    loadContent(currentUrl, false);
});
