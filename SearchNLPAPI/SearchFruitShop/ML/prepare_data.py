import requests
def get_file_lines(file_path):
    lines = []
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            for line in file:
                lines.append(line.strip())
    except FileNotFoundError:
        print(f"File not found: {file_path}")
    return lines
#Lấy dữ liệu từ api
def fetch_data_from_api():
    api_url = "http://127.0.0.1:8000/api/all"
    try:
        response = requests.get(api_url)
        response.raise_for_status()  # Kiểm tra lỗi HTTP
        data = response.json()  # Chuyển đổi dữ liệu JSON thành Python object (list, dict)
        return data  # Giả định data là một danh sách chuỗi văn bản
    except requests.RequestException as e:
        print(f"Error fetching data from API: {e}")
        return []
# Lấy dữ liệu từ db
def get_product_details(products):
    details = []
    for product in products:
        detail = product['detail']
        if ((detail is not None) and (detail != '')):
            details.append(detail)
    return details
