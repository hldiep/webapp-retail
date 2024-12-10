from .ML.embedding import train_word2vec_model, get_word2vec_vector
from .ML.prepare_data import create_dict_products
from .models import Product
from .ML.preprocess_use_pyvi import preprocess_data_form_dict
def get_products_from_db():
    return Product.objects.filter(flag=False)
def get_dict_preprocessed_products(products):
    #Lưu sản phẩm dưới dạng: id - chi tiết sản phẩm
    dict_products = create_dict_products(products)
    # Lưu vào dictionary mới với id sản phẩm là key và preprocessed detail là value
    return preprocess_data_form_dict(dict_products)

dict_preprocessed_products = get_dict_preprocessed_products(get_products_from_db())
#Train
word2vec_model = train_word2vec_model(list(dict_preprocessed_products.values()))
def get_dict_product_vectors(dict_preprocessed_products):
    dict_product_vectors={}
    # Tính toán vector cho từng sản phẩm
    for product_id, preprocessed_detail in dict_preprocessed_products.items():
        product_vector = get_word2vec_vector(preprocessed_detail, word2vec_model)
        dict_product_vectors[product_id] = product_vector
    return dict_product_vectors

dict_product_vectors = get_dict_product_vectors(dict_preprocessed_products)



# products = get_products_from_db()
# dict_products = create_dict_products(products)
# #test
# first_key = next(iter(dict_products))
# print(first_key)
# print(dict_products[first_key])
# #################
# #Lưu vào dictionary mới với id sản phẩm là key và preprocessed detail là value
# dict_preprocessed_products = preprocess_data_form_dict(dict_products)
# #test
# first_key = next(iter(dict_preprocessed_products))
# print(first_key)
# print(dict_preprocessed_products[first_key])
#################
# # Tính toán vector cho từng sản phẩm
# for product_id, preprocessed_detail in dict_preprocessed_products.items():
#     product_vector = get_word2vec_vector(preprocessed_detail, word2vec_model)
#     dict_product_vectors[product_id] = product_vector

# details = get_product_details(products)
# preprocessed_details = preprocess_data(details)
# word2vec_model = train_word2vec_model(preprocessed_details)
# product_vectors = [get_word2vec_vector(preprocessed, word2vec_model) for preprocessed in preprocessed_details]

