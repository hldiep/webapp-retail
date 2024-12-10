# Generated by Django 5.0.9 on 2024-11-13 11:32

import django.db.models.deletion
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Category',
            fields=[
                ('id', models.CharField(max_length=255, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255)),
                ('flag', models.BooleanField()),
            ],
            options={
                'db_table': 'Category',
            },
        ),
        migrations.CreateModel(
            name='Nutrient',
            fields=[
                ('id', models.CharField(max_length=255, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255)),
                ('detail', models.TextField(blank=True, null=True)),
                ('flag', models.BooleanField()),
            ],
            options={
                'db_table': 'Nutrient',
            },
        ),
        migrations.CreateModel(
            name='Unit',
            fields=[
                ('id', models.CharField(max_length=255, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255)),
                ('flag', models.BooleanField()),
            ],
            options={
                'db_table': 'Unit',
            },
        ),
        migrations.CreateModel(
            name='Product',
            fields=[
                ('id', models.CharField(max_length=255, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255)),
                ('detail', models.TextField(blank=True, null=True)),
                ('price', models.DecimalField(decimal_places=2, default=0.0, max_digits=10)),
                ('flag', models.BooleanField(default=False)),
                ('category', models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.SET_NULL, to='search.category')),
            ],
            options={
                'db_table': 'Product',
            },
        ),
        migrations.CreateModel(
            name='ProductNutrient',
            fields=[
                ('id', models.CharField(max_length=255, primary_key=True, serialize=False)),
                ('flag', models.BooleanField()),
                ('nutrient', models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='search.nutrient')),
                ('product', models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='search.product')),
            ],
            options={
                'db_table': 'Product_nutrient',
            },
        ),
        migrations.AddField(
            model_name='product',
            name='nutrients',
            field=models.ManyToManyField(through='search.ProductNutrient', to='search.nutrient'),
        ),
        migrations.AddField(
            model_name='nutrient',
            name='products',
            field=models.ManyToManyField(through='search.ProductNutrient', to='search.product'),
        ),
        migrations.AddField(
            model_name='product',
            name='unit',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.SET_NULL, to='search.unit'),
        ),
    ]
