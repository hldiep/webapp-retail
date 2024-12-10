from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Product
from .serializers import ProductSerializer
from django.http import HttpResponse
from .ML.preprocess_use_pyvi import custom_preprocessing
from .ml_units import word2vec_model, dict_product_vectors
from sklearn.metrics.pairwise import cosine_similarity
import logging

from .ML.embedding import get_word2vec_vector

logger = logging.getLogger(__name__)

def home(request):
    products = Product.objects.all()
    print(products)
    return HttpResponse("Welcome to Fruit Shop!")

class search_product(APIView):
    def get(self, request):
        query = request.GET.get('query', '')
        #tiền xử lý
        processed_query = custom_preprocessing(query)
        query_vector = get_word2vec_vector(processed_query, word2vec_model)
        print(processed_query)
        similarities = []
        for product_id, vector in dict_product_vectors.items():
            similarity_score = cosine_similarity([query_vector], [vector])[0][0]
            similarities.append((product_id, similarity_score))
            print(f"Product ID: {product_id}, Similarity Score: {similarity_score}")
        # Sắp xếp theo độ tương đồng giảm dần và chọn phần tử có độ tương đồng cao nhất
        # most_similar_product = sorted(similarities, key=lambda x: x[1], reverse=True)[0][0]
        sorted_similarities = sorted(similarities, key=lambda x: x[1], reverse=True)
        highest_similarity_score = sorted_similarities[0][1]
        max_difference = 0.1
        most_similar_products = []
        for product_id, similarity_score in sorted_similarities:
            if similarity_score < 0.2:
                break
            elif similarity_score >= 0.45:
                most_similar_products.append(product_id)
            elif highest_similarity_score - similarity_score <=max_difference:
                most_similar_products.append(product_id)
            else:
                break  # Dừng khi độ chênh lệch vượt quá ngưỡng cho phép

        # top_n = 5
        # most_similar_products = [product_id for product_id, score in sorted_similarities[:top_n]]
        # Trả về phần tử có độ tương đồng cao nhất
        return Response(most_similar_products)

class SearchProduct(APIView):
    def get(self, request):
        query = request.query_params.get('query', '')
        print(f"Search query: {query}")  # Debugging print statement
        try:
            if query:
                # Lọc sản phẩm dựa trên các trường có trong model Product
                products = (Product.objects.filter(name__icontains=query)
                            | Product.objects.filter(detail__icontains=query)
                            | Product.objects.filter(id__icontains=query))
            else:
                products = Product.objects.all()

            serializer = ProductSerializer(products, many=True)
            return Response(serializer.data)
        except Exception as e:
            logger.error(f"Lỗi khi lấy sản phẩm: {e}")
            return Response({"error": "Lỗi máy chủ nội bộ"}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

class getAllProduct(APIView):
    def get(self, request):
        products = Product.objects.all()
        serializer = ProductSerializer(products, many=True)
        return Response(serializer.data)
