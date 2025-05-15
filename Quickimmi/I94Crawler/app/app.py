import json
import time

import boto3
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


s3 = boto3.client('s3', region_name="us-east-1")
options = Options()
options.add_argument('--no-sandbox')
options.add_argument('--single-process')
options.add_argument('--disable-dev-shm-usage')
options.add_argument("--window-size=1280x1696")
options.binary_location = '/opt/headless-chromium'
# to pass the permission
options.add_argument('--user-agent=selenium/4.1.4 (java mac)')
options.add_argument('--content-type=application/json; charset=utf-8')
options.add_argument('--accept=*/*')
options.add_argument('--cache-control=no-cache')  # Adjust the language code as needed


def lambda_handler(event, context):
    driver = webdriver.Chrome('/opt/chromedriver', chrome_options=options)
    driver.set_page_load_timeout(100)
    driver.get("https://i94.cbp.dhs.gov/I94/#/home")

    # Click on the most recent element
    most_recent = driver.find_element(By.CLASS_NAME, "most-recent")
    most_recent.click()
    time.sleep(2)

    # Click on the consent button
    consent_button = driver.find_element(By.ID, "consent")
    consent_button.click()
    print("click")
    #time.sleep(2)

    # Change the info to the correct info, especially the passport number
    fname = "Jingtao"
    lname = "Han"
    dob_day = "21"
    dob_month = "04"
    dob_year = "1997"
    id_num = "XXXXXXX"
    country = "CHN"

    input_field = driver.find_element(By.ID, "firstName")
    print("firstName %s", input_field.get_attribute("outerHTML"))
    input_field.send_keys(fname)
    print("fname")
    input_field = driver.find_element(By.ID, "lastName")
    input_field.send_keys(lname)
    print("lname")
    input_field = driver.find_element(By.ID, "birthDay")
    print("birthDay %s", input_field)
    input_field.send_keys(dob_day)
    print("bday")
    select_element = driver.find_element(By.ID, "birthMonth")
    select = Select(select_element)
    select.select_by_value(dob_month)
    print("dob_month")
    input_field = driver.find_element(By.ID, "birthYear")
    input_field.send_keys(dob_year)
    print("dob_year")
    input_field = driver.find_element(By.ID, "passportNumber")
    input_field.send_keys(id_num)
    print("id_num")
    select_element = driver.find_element(By.ID, "passportCountry")
    select = Select(select_element)
    select.select_by_value(country)
    print("country")
    next_button = driver.find_element(By.CLASS_NAME, "btn-next")
    print("next_button %s", next_button)
    next_button.click()
    print("next_button clicked")
    # if we uncomment the following line, the code will stuck and never proceed to screenshot
    #time.sleep(5)
    # if we don't uncomment the following line, the code will take a screenshot showing the loading "Please wait..."
    driver.save_screenshot("/tmp/screenshot.png")
    print("screenshot saved")

    # when invoking locally, the first time the code will stuck at a random point
    # if we restart the docker and rebuild and run the code, the code will run smoothly


    '''
    # Wait until the reCAPTCHA checkbox element is located
    recaptcha = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.CLASS_NAME, "recaptcha-checkbox-border"))
    )
    element_html = recaptcha.get_attribute("outerHTML")
    # Print the HTML content of the element
    print(element_html)


    i94 = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.CLASS_NAME, "record-content"))
    )
    element_html = i94.get_attribute("outerHTML")
    # Print the HTML content of the element
    print(element_html)
    '''
    time.sleep(100)
    driver.quit()
