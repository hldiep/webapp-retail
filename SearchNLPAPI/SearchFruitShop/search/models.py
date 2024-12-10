from django.db import models
from django.utils import timezone

# Create your models here.
class Category(models.Model):
    id = models.CharField(primary_key=True, max_length=255)
    name = models.CharField(max_length=255, null=False)
    flag = models.BooleanField(null=False)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'Category'

class Unit(models.Model):
    id = models.CharField(primary_key=True, max_length=255)
    name = models.CharField(max_length=255, null=False)
    flag = models.BooleanField(null=False)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'Unit'

class Nutrient(models.Model):
    id = models.CharField(primary_key=True, max_length=255)
    name = models.CharField(max_length=255, null=False)
    detail = models.TextField(blank=True, null=True)
    flag = models.BooleanField(null=False)
    products = models.ManyToManyField('Product', through='ProductNutrient')
    def __str__(self):
        return self.name
    class Meta:
        db_table = 'Nutrient'

class Product(models.Model):
    id = models.CharField(max_length=255, primary_key=True)
    name = models.CharField(max_length=255)
    detail = models.TextField(null=True, blank=True)
    price = models.DecimalField(max_digits=10, decimal_places=2, default=0.0)
    category = models.ForeignKey(Category, on_delete=models.SET_NULL, null=True, blank=True)
    unit = models.ForeignKey(Unit, on_delete=models.SET_NULL, null=True, blank=True)
    flag = models.BooleanField(default=False)
    nutrients = models.ManyToManyField(Nutrient, through='ProductNutrient')

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'Product'

class ProductNutrient(models.Model):
    id = models.CharField(primary_key=True, max_length=255)
    product = models.ForeignKey(Product, on_delete=models.CASCADE, null=True, blank=True)
    nutrient = models.ForeignKey(Nutrient, on_delete=models.CASCADE, null=True, blank=True)
    flag = models.BooleanField(null=False)

    def __str__(self):
        return f'{self.product.name} - {self.nutrient.name if self.nutrient else "No Nutrient"}'
    class Meta:
        db_table = 'Product_nutrient'