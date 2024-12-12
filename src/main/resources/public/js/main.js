$(() => {
    var saveRequestHandler = new SaveRequest();
    $(".saveForm").on('submit', saveRequestHandler.submit);
})