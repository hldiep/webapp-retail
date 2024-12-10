
from sklearn.metrics.pairwise import cosine_similarity
import SearchFruitShop.search.ML.embedding as embedding
# Hàm tính toán độ tương đồng cho tìm kiếm
product_vectors = embedding.product_vectors

def search_products(query):
    query_vector = embedding.get_word2vec_vector(query)
    results = []
    for product_vector in product_vectors:
        # Tính toán độ tương đồng cosine
        similarity = cosine_similarity([query_vector], [product_vector])[0][0]
        results.append((embedding.products['name'], similarity))
    # Sắp xếp theo độ tương đồng giảm dần
    results.sort(key=lambda x: x[1], reverse=True)
    return results