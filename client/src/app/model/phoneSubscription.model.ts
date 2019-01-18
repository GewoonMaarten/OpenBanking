export class PhoneSubscription {
    id: number;
    provider: string;

    sms: number;
    minutes: number;
    internetGB: number;

    price: number;
    connectionCost: number;
    discount: boolean;
    renwableAfter: number;
    duration: number;
    description: string;
}