================================================================================================================================
										ChromeDriver install | https://chromedriver.chromium.org/home
================================================================================================================================


# Скачать аткуальеый архив для Linux :

https://chromedriver.chromium.org/downloads

# Перейти в папку с распаковванным архивом:

cd /home/andtokm/Downloads/chromedriver_linux64


sudo mv chromedriver /usr/local/bin/chromedriver
sudo chown andtokm:andtokm /usr/local/bin/chromedriver
sudo chmod +x /usr/local/bin/chromedriver


# FIX: Java.io.IOException: Invalid Status code=403 text=Forbidden

options.addArguments("--remote-allow-origins=*");
