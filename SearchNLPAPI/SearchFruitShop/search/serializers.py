from rest_framework import serializers
from .models import *



class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category
        fields = ['name']


class UnitSerializer(serializers.ModelSerializer):
    class Meta:
        model = Unit
        fields = ['name']


class NutrientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Nutrient
        fields = ['name']

class ProductSerializer(serializers.ModelSerializer):
    category = CategorySerializer(read_only=True)
    unit = UnitSerializer(read_only=True)
    nutrients = serializers.SerializerMethodField()
    class Meta:
        model = Product
        fields = '__all__'

    def get_nutrients(self, obj):
        # Trường hợp không có nutrient liên kết với product
        if obj.nutrients.exists():
            return obj.nutrients.values_list('nutrient__name', flat=True)
        return []  # Trả về danh sách rỗng nếu không có nutrient nào