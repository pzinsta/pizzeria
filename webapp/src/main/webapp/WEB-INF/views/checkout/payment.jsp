<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <script src="https://js.braintreegateway.com/web/dropin/1.10.0/js/dropin.min.js"></script>
    </head>
    <body>
        payment
        ${orderCost}
        ${deliveryCost}
        <form:form method="post" id="paymentForm" autocomplete="false">

            <input type="hidden" id="nonce" name="payment_method_nonce" />
            <input type="hidden" name="_eventId" value="continue">

            <div id="dropin-container"></div>

            <button class="button" type="submit">Purchase</button>
            <a href="${flowExecutionUrl}&_eventId=cancel">Cancel</a>
        </form:form>

        <script>

            var form = document.querySelector('#paymentForm');
            var client_token = '${clientToken}';

            braintree.dropin.create({
                authorization: client_token,
                container: '#dropin-container',
                paypal: {
                    flow: 'vault'
                }
            }, function (createErr, instance) {
                form.addEventListener('submit', function (event) {
                    event.preventDefault();
                    instance.requestPaymentMethod(function (err, payload) {
                        if (err) {
                            console.log('Error', err);
                            return;
                        }
                        // Add the nonce to the form and submit
                        document.querySelector('#nonce').value = payload.nonce;
                        form.submit();
                    });
                });
            });
        </script>
    </body>
</html>
