@echo off
Get-Content C:\Users\Antony\Desktop\C2ImportFamRelSample1.csv |%{$_.Split(',')[0]}