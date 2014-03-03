function initExchangeStream() {
  var socket = $.atmosphere;

  var request = new $.atmosphere.AtmosphereRequest();
  request.url = document.location.toString() + 'stream/trades';
  request.contentType = "application/json";
  request.transport = 'websocket';
  request.fallbackTransport = 'long-polling';

  var $body = $('body');

  request.onOpen = function(response) {

    $body.append($('<p>',
      { text: 'Atmosphere connected using ' + response.transport }));
  };

  request.onMessage = function(response) {
    var message = response.responseBody;
    try {
      var json = JSON.parse(message);
    } catch (e) {
      console.log('Error: ', message.data);
      return
    }

    var buyTriggered = '';
    var sellTriggered = '';
    if (json.triggeringSide == 'BUY') {
      buyTriggered = '*';
    } else {
      sellTriggered = '*';
    }

    var trade = json.trade;

    $body.append($('<p>',
      { text: 'Trade: ' + trade.ticker.symbol +
      ' buyer: ' + trade.buySideBroker + buyTriggered +
      ' seller: ' + trade.sellSideBroker + sellTriggered +
      ' price: ' + trade.price.raw +
      ' qty: ' + trade.quantity.raw }));
  };

  request.onTransportFailure = function(errorMessage, request) {
//    alert('transport failed: ' + request + '\n' + errorMessage);
  };

  var subSocket = socket.subscribe(request);
}
