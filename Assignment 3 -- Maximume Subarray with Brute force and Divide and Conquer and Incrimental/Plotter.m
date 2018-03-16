%Plotter
clear
close all
[num, txt, raw] = xlsread('Output.xlsx');

bf = 1:3:16;
dac = 2:3:17;
kad = 3:3:18;

figure
hold on
plot(num(bf,1), num(bf, 3))
plot(num(dac,1), num(dac, 3))
plot(num(kad,1), num(kad, 3))

title('All data')
ylabel('Time in seconds')
xlabel('Size of input')
legend('Brute Force', 'Divide and Conquer', 'Kadane''s')

figure
hold on
plot(num(bf,1), num(bf, 3))
p = polyfit(num(bf,1), num(bf, 3), 2);
y = polyval(p,0:65536);
plot(0:65536, y)

title('Brute Force')
ylabel('Time in seconds')
xlabel('Size of input')
legend('Data', 'Quadratic best fit')

figure
hold on
plot(num(dac,1), num(dac, 3))

title('Divide and Conquer')
ylabel('Time in seconds')
xlabel('Size of input')


figure
hold on
plot(num(kad,1), num(kad, 3))

title('Kadane''s')
ylabel('Time in seconds')
xlabel('Size of input')

figure
hold on
plot(num(dac,1), num(dac, 3))
plot(num(kad,1), num(kad, 3))
title('Comparing the smaller two algorithms')
ylabel('Time in seconds')
xlabel('Size of input')
legend('Divide and conquer', 'Kadane''s')






