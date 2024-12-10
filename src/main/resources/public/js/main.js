$(() => {
    var saveRequestHandler = new SaveRequest();

    console.log($(".saveForm"));
    $(".saveForm").on('submit', saveRequestHandler.submit);
})