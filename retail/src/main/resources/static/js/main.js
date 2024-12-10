/*  ---------------------------------------------------
    Template Name: Ogani
    Description:  Ogani eCommerce  HTML Template
    Author: Colorlib
    Author URI: https://colorlib.com
    Version: 1.0
    Created: Colorlib
---------------------------------------------------------  */

'use strict';

(function ($) {

    /*------------------
        Preloader
    --------------------*/
    $(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloder").delay(200).fadeOut("slow");

        /*------------------
            Gallery filter
        --------------------*/
        $('.featured__controls li').on('click', function () {
            $('.featured__controls li').removeClass('active');
            $(this).addClass('active');
        });
        if ($('.featured__filter').length > 0) {
            var containerEl = document.querySelector('.featured__filter');
            var mixer = mixitup(containerEl);
        }
    });

    /*------------------
        Background Set
    --------------------*/
    $('.set-bg').each(function () {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    //Humberger Menu
    $(".humberger__open").on('click', function () {
        $(".humberger__menu__wrapper").addClass("show__humberger__menu__wrapper");
        $(".humberger__menu__overlay").addClass("active");
        $("body").addClass("over_hid");
    });

    $(".humberger__menu__overlay").on('click', function () {
        $(".humberger__menu__wrapper").removeClass("show__humberger__menu__wrapper");
        $(".humberger__menu__overlay").removeClass("active");
        $("body").removeClass("over_hid");
    });

    /*------------------
		Navigation
	--------------------*/
    $(".mobile-menu").slicknav({
        prependTo: '#mobile-menu-wrap',
        allowParentLinks: true
    });

    /*-----------------------
        Categories Slider
    ------------------------*/
    $(".categories__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 4,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            0: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 3,
            },

            992: {
                items: 4,
            }
        }
    });


    $('.hero__categories__all').on('click', function(){
        $('.hero__categories ul').slideToggle(400);
    });

    /*--------------------------
        Latest Product Slider
    ----------------------------*/
    $(".latest-product__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 1,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*-----------------------------
        Product Discount Slider
    -------------------------------*/
    $(".product__discount__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 3,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            320: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 2,
            },

            992: {
                items: 3,
            }
        }
    });

    /*---------------------------------
        Product Details Pic Slider
    ----------------------------------*/
    $(".product__details__pic__slider").owlCarousel({
        loop: true,
        margin: 20,
        items: 4,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*-----------------------
		Price Range Slider
	------------------------ */
    var rangeSlider = $(".price-range"),
    minamount = $("#minamount"),
    maxamount = $("#maxamount"),
    minPrice = rangeSlider.data('min'),
    maxPrice = rangeSlider.data('max');
    rangeSlider.slider({
        range: true,
        min: minPrice,
        max: maxPrice,
        values: [minPrice, maxPrice],
        slide: function (event, ui) {
            minamount.val('$' + ui.values[0]);
            maxamount.val('$' + ui.values[1]);
        }
    });
    minamount.val('$' + rangeSlider.slider("values", 0));
    maxamount.val('$' + rangeSlider.slider("values", 1));

    /*--------------------------
        Select
    ----------------------------*/
    $("select").niceSelect();

    /*------------------
		Single Product
	--------------------*/
    $('.product__details__pic__slider img').on('click', function () {

        var imgurl = $(this).data('imgbigurl');
        var bigImg = $('.product__details__pic__item--large').attr('src');
        if (imgurl != bigImg) {
            $('.product__details__pic__item--large').attr({
                src: imgurl
            });
        }
    });

    /*-------------------
		Quantity change
	--------------------- */
    var proQty = $('.pro-qty');
    //proQty.prepend('<span class="dec qtybtn">-</span>');
    //proQty.append('<span class="inc qtybtn">+</span>');
    proQty.on('click', '.qtybtn', function () {
        var $button = $(this);
        var productId = $button.data('product-id');
        console.log(productId);
        var oldValue = $button.parent().find('input').val();
        if ($button.hasClass('inc')) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            // Don't allow decrementing below zero
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 0;
            }
        }
        //Format các giá trị theo loại tiền VND
        $button.parent().find('input').val(newVal.toLocaleString('en-US'));
        var element_id= 'price-'+productId;
        var total_element_id='total-'+productId;
        const priceElement = document.getElementById(element_id);
        const priceText = priceElement.innerText;
        const numericPrice = parseFloat(priceText.replace(/[^0-9.-]+/g,""));
        const totalElement = document.getElementById(total_element_id);
        //console.log(totalElement);
        const totalAmount = (numericPrice * newVal);
        totalElement.innerText = totalAmount.toLocaleString('en-US') + ' VND';
        //Tính tổng số tiền cần thanh toán
        updateGrandTotal();
    });

    // Khi người dùng click "Cập nhật giỏ hàng"
    $('#update-cart-button').click(function(e) {
        e.preventDefault();
        var quantities = {};
        $('tbody tr').each(function() {
            var productId = $(this).find('.qtybtn').data('product-id');
            var quantity = $(this).find('.pro-qty input').val();
            if (productId && !isNaN(quantity)) {
                quantities[productId] = quantity;
            }
        });

        // AJAX request to update cart
        $.ajax({
            type: 'POST',
            url: 'cart/update',
            data: quantities,
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('Có lỗi xảy ra : ' + error);
            }
        });
    });

    //Khi người dùng click vào TIẾN HÀNH THANH TOÁN
    $('#checkout-button').on('click', function(event) {
        event.preventDefault();
        window.location.href = 'thanh-toan';
    });

    // Khi người dùng xóa một sản phẩm trong cart
    $('.icon_close').on('click', function() {
        const productId = $(this).data('product-id');
        console.log(productId);
        this.closest("tr").remove();
        //Tính tổng số tiền cần thanh toán
        updateGrandTotal();
    });

    function updateGrandTotal() {
        let grandTotal = 0;
        document.querySelectorAll(".shoping__cart__total").forEach(totalElement => {
            const itemTotal = parseFloat(totalElement.textContent.replace(" VND", "").replace(",", ""));
            grandTotal += itemTotal;
        });
        document.getElementById("grand-total").innerText = grandTotal.toLocaleString('en-US') + " VND";
        document.getElementById("grand-total-final").innerText = grandTotal.toLocaleString('en-US') + " VND";
    }

})(jQuery);