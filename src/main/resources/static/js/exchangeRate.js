$(document).ready(function(){
    changeRate();
})

function submitClick() {
    let getAmount = $("#amount").val();
    getAmount = parseInt(getAmount);

    if (!getAmount) {
        alert("송금액이 바르지 않습니다");
    } else if (getAmount < 0) {
        alert("송금액이 바르지 않습니다");
    } else if (getAmount > 10000) {
        alert("송금액이 바르지 않습니다");
    } else if (isNaN(getAmount)) {
        alert("송금액이 바르지 않습니다");
    } else {
        let countrySelect = document.getElementById("countrySelect");
        let selectVal = countrySelect.options[countrySelect.selectedIndex].value;

        $.ajax({
            type:"POST",
            url:"/rate/exchangeRate_submit",
            data:{amount:getAmount, country:selectVal},
            async: false
        }).done(function(res) {
            document.querySelector("html").innerHTML = res;
            $("#countrySelect").val(selectVal).prop("selected", true);
            console.log("exchangeRate_submit success !");
        }).fail(function(res) {
            console.log("exchangeRate_submit fail!");
        });
    }
}

function changeRate() {
    $.ajax({
        url:"http://api.currencylayer.com/live?access_key=6de1dc73c2da5a37aac341f6580b8c41",
        dataType:"json",
        async: false
    }).done(function(res) {
        let quotes = res.quotes;
        let rateData = {};

        for (key in quotes) {
            if (key === "USDJPY") {
                rateData[key] = quotes[key].toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            } else if (key === "USDKRW") {
                rateData[key] = quotes[key].toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            } else if (key === "USDPHP") {
                rateData[key] = quotes[key].toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
        }
        reqRate(rateData);
    }).fail(function(res) {
        console.log("currencylayer Api fail!");
    });
}

function reqRate(rateData) {
    $.ajax({
        type:"POST",
        url:"/rate/exchangeRate_save",
        data:rateData,
        async: false
    }).done(function(res) {
        document.querySelector("html").innerHTML = res;
        console.log("exchangeRate_save success!");
    }).fail(function(res) {
        console.log("exchangeRate_save fail!");
    });
}

function changeCountry() {
    let countrySelect = document.getElementById("countrySelect");
    let selectVal = countrySelect.options[countrySelect.selectedIndex].value;

    $.ajax({
        type:"POST",
        url:"/rate/exchangeRate_change",
        data:{country:selectVal},
        async: false
    }).done(function(res) {
        document.querySelector("html").innerHTML = res;
        $("#countrySelect").val(selectVal).prop("selected", true);
        console.log("exchangeRate_change success!");
    }).fail(function(res) {
        console.log("exchangeRate_change fail!");
    });
}