const PaymentService = require('../src/payment-service');

describe('PaymentService', () => {
  let paymentService;

  beforeEach(() => {
    paymentService = new PaymentService();
  });

  describe('constructor', () => {
    it('should create a new PaymentService instance', () => {
      expect(paymentService).toBeInstanceOf(PaymentService);
    });
  });

  describe('validateCreditCard', () => {
    it('should throw error for unimplemented method', () => {
      const cardData = {
        number: '4111111111111111',
        expiryMonth: '12',
        expiryYear: '2025',
        cvv: '123'
      };

      expect(() => {
        paymentService.validateCreditCard(cardData);
      }).toThrow('Card validation not implemented yet');
    });
  });

  // TODO: Add more tests as methods are implemented
  // These tests serve as examples for workshop participants
});