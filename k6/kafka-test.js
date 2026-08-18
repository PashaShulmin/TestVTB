import { Writer } from 'k6/x/kafka';
import encoding from 'k6/encoding';

const writer = new Writer({
    brokers: ['kafka:29092'],
    topic: 'input',
});

export const options = {
    scenarios: {
        kafka_load: {
            executor: 'ramping-arrival-rate',

            startRate: 5,
            timeUnit: '1s',

            preAllocatedVUs: 10,
            maxVUs: 50,

            stages: [
                { target: 5, duration: '5m' },
                { target: 10, duration: '0s' },
                { target: 10, duration: '5m' },
            ],
        },
    },
//    vus: 1,
//    iterations: 1,
};

export default function () {
    const userId = Math.floor(Math.random() * 1000000).toString();

    writer.produce({
        messages: [
            {
                key: userId,
                value: userId,
            },
        ],
    });
}