from django.urls import path
from . import views

urlpatterns = [
    path('', views.home, name='home'),
    path('all', views.getAllProduct.as_view(), name='all_product'),
    path('search/', views.SearchProduct.as_view(), name='search_product'),
    path('search_ml/', views.search_product.as_view(), name="search_product_ml")
]