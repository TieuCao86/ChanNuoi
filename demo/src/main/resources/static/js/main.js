// ====================
// MAIN.JS - Dùng chung
// ====================

// Load nội dung vào #main-content và gắn script tương ứng
function loadContent(url, push = true) {
    const [path, queryString] = url.split('?');
    const urlParams = new URLSearchParams(queryString || '');

    fetch(path)
        .then(response => {
            if (!response.ok) throw new Error('Lỗi khi tải nội dung');
            return response.text();
        })
        .then(html => {
            document.getElementById('main-content').innerHTML = html;

            if (push) history.pushState(null, '', url);

            if (path.startsWith('/khu-nuoi')) {
                loadScriptAndInit('/js/khu-nuoi.js', 'initKhuNuoi');

            } else if (path.startsWith('/chuong-nuoi')) {
                loadScriptAndInit('/js/chuong-nuoi.js', () => {
                    const maKhu = urlParams.get("maKhu");
                    const tenKhu = urlParams.get("tenKhu");

                    // Nếu là trang chi tiết chuồng
                    if (path.startsWith('/chuong-nuoi/detail') && typeof loadChuongDetail === 'function') {
                        loadChuongDetail();
                    }
                    // Nếu là danh sách chuồng (có hoặc không có bộ lọc khu)
                    else if (typeof initChuong === 'function') {
                        initChuong(maKhu, tenKhu);
                    }

                    if (typeof initFilters === 'function') {
                        initFilters();
                    }
                });
            } else if (path.startsWith('/kho/')) {
                loadScriptAndInit('/js/kho.js', 'initKho');
            }
        })
        .catch(error => {
            document.getElementById('main-content').innerHTML =
                `<div class="alert alert-danger">${error.message}</div>`;
        });
}

// Tải file JS và chạy hàm khởi tạo (tên hoặc callback)
function loadScriptAndInit(src, initCallbackOrFunctionName) {
    const existing = document.querySelector(`script[src="${src}"]`);
    const runInit = () => {
        if (typeof initCallbackOrFunctionName === 'function') {
            initCallbackOrFunctionName();
        } else if (typeof window[initCallbackOrFunctionName] === 'function') {
            window[initCallbackOrFunctionName]();
        } else {
            console.warn(`⚠️ Không tìm thấy hàm khởi tạo: ${initCallbackOrFunctionName}`);
        }
    };

    if (existing) {
        runInit();
        return;
    }

    const script = document.createElement('script');
    script.src = src;
    script.defer = true;
    script.onload = runInit;
    script.onerror = () => {
        console.error(`❌ Không thể tải file script: ${src}`);
        alert(`Không thể tải script: ${src}`);
    };
    document.body.appendChild(script);
}

// Toggle submenu sidebar
function toggleSubmenu(element) {
    element.classList.toggle('collapsed');
    const submenu = element.nextElementSibling;
    submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
}

// Xử lý submit form POST hoặc PUT tùy theo có ID hay không
function handleFormSubmit(formId, apiUrl, getPayload, afterSubmit) {
    const form = document.getElementById(formId);
    if (!form) return;

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const id = form.querySelector("input[type=hidden]").value.trim();
        const payload = getPayload();
        const method = id ? "PUT" : "POST";
        const url = id ? `${apiUrl}/${id}` : apiUrl;

        fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        }).then(() => afterSubmit());
    });
}

// Debounce function dùng chung
function debounce(func, delay) {
    let timeout;
    return function () {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, arguments), delay);
    };
}

// Lọc client-side: áp dụng filter vào mảng dữ liệu
function applyFilters(data, filters) {
    return data.filter(item => {
        return Object.entries(filters).every(([key, value]) => {
            if (!value) return true;
            const itemValue = item[key];
            if (itemValue == null) return false;
            return itemValue.toString().toLowerCase().includes(value.toString().toLowerCase());
        });
    });
}

// Khởi tạo lọc server-side (có debounce), gọi onChange khi input thay đổi
function initFilters(filterIds, onChange, debounceDelay = 300) {
    const debounced = debounce(onChange, debounceDelay);
    filterIds.forEach(id => {
        const element = document.getElementById(id);
        if (element) {
            const eventType = (element.tagName === 'SELECT') ? 'change' : 'input';
            element.addEventListener(eventType, eventType === 'input' ? debounced : onChange);
        }
    });
}

// Quay lại lịch sử khi bấm nút Back
window.addEventListener('popstate', () => {
    const currentUrl = window.location.pathname + window.location.search;
    loadContent(currentUrl, false);
});

function renderTable({ url, data, bodyElementId, columns, rowClickUrlBuilder }) {
    const body = document.getElementById(bodyElementId);
    if (!body) return;

    const render = (items) => {
        body.innerHTML = '';

        if (!items || items.length === 0) {
            const row = document.createElement("tr");
            const cell = document.createElement("td");
            cell.colSpan = columns.length;
            cell.className = "text-center text-muted";
            cell.textContent = "Không có dữ liệu.";
            row.appendChild(cell);
            body.appendChild(row);
            return;
        }

        items.forEach(item => {
            const row = document.createElement("tr");

            columns.forEach(col => {
                const td = document.createElement("td");
                if (col.type === "actions") {
                    col.actions.forEach(action => {
                        const btn = document.createElement("button");
                        btn.className = `btn btn-sm ${action.className} me-1`;
                        btn.textContent = action.label;
                        btn.addEventListener("click", (e) => {
                            e.stopPropagation();
                            action.onClick(item);
                        });
                        td.appendChild(btn);
                    });
                } else {
                    const value = item[col.key];
                    td.textContent = col.formatter ? col.formatter(value) : value;
                }
                row.appendChild(td);
            });

            if (rowClickUrlBuilder) {
                row.style.cursor = "pointer";
                row.addEventListener("click", () => {
                    const url = rowClickUrlBuilder(item);
                    loadContent(url);
                    window.history.pushState({}, '', url);
                });
            }

            body.appendChild(row);
        });
    };

    if (data) {
        render(data);
    } else if (url) {
        fetch(url)
            .then(res => res.json())
            .then(render)
            .catch(err => {
                console.error("Lỗi khi tải dữ liệu:", err);
                body.innerHTML = `<tr><td colspan="${columns.length}" class="text-danger text-center">Lỗi khi tải dữ liệu</td></tr>`;
            });
    }
}


