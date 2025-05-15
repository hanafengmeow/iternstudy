``
## Customer table
~~~
CREATE TABLE customer_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    cognito_username VARCHAR(100) UNIQUE NOT NULL,
    status INT,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    middle_name VARCHAR(100),
    birth_date VARCHAR(20), -- Assuming mm/dd/yy format
    nationality VARCHAR(100),
    gender VARCHAR(20),
    birth_place VARCHAR(100),
    address JSON,
    stripe_customer_id VARCHAR(255),
    stripe_account_id VARCHAR(255),
    stripe_payment_method_id VARCHAR(255),
    last_login_at BIGINT,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE `customer_table` ( `id` int(11) NOT NULL AUTO_INCREMENT, `username` varchar(100) DEFAULT NULL, `email` varchar(100) DEFAULT NULL, `phone_number` varchar(20) DEFAULT NULL, `cognito_username` varchar(100) NOT NULL, `status` int(11) DEFAULT NULL, `first_name` varchar(100) DEFAULT NULL, `last_name` varchar(100) DEFAULT NULL, `middle_name` varchar(100) DEFAULT NULL, `birth_date` varchar(10) DEFAULT NULL, `nationality` varchar(100) DEFAULT NULL, `gender` varchar(10) DEFAULT NULL, `birth_place` varchar(100) DEFAULT NULL, `address` json DEFAULT NULL, `stripe_customer_id` varchar(255) DEFAULT NULL, `stripe_account_id` varchar(255) DEFAULT NULL, `stripe_payment_method_id` varchar(255) DEFAULT NULL, `last_login_at` bigint(20) DEFAULT NULL, `created_at` bigint(20) DEFAULT NULL, `updated_at` bigint(20) DEFAULT NULL, PRIMARY KEY (`id`), UNIQUE KEY `cognito_username` (`cognito_username`) ) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=latin1

~~~
## Case Table
~~~
CREATE TABLE application_case_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    case_name VARCHAR(255),
    applicant_name VARCHAR(255),
    type VARCHAR(100),
    email VARCHAR(100),
    current_step VARCHAR(100),
    progress JSON,
    status VARCHAR(100), 
    profile JSON,
    submitted INT,  
    submitted_at BIGINT,
    uscis_receipt_number VARCHAR(100),
    assigned_lawyer INT,
    created_by INT,
    created_at BIGINT,
    updated_at BIGINT
 );
~~~
CREATE TABLE `application_case_table` ( `id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) DEFAULT NULL, `applicant_name` varchar(255) DEFAULT NULL, `type` varchar(100) DEFAULT NULL, `description` varchar(10000) DEFAULT NULL, `reason` varchar(255) DEFAULT NULL, `current_step` varchar(100) DEFAULT NULL, `percentage` varchar(1000) DEFAULT NULL, `profile` json DEFAULT NULL, `translated_profile` json DEFAULT NULL, `submitted` int(11) DEFAULT NULL, `submitted_at` bigint(20) DEFAULT NULL, `uscis_receipt_number` varchar(100) DEFAULT NULL, `assigned_lawyer` int(11) DEFAULT NULL, `created_at` bigint(20) DEFAULT NULL, `updated_at` bigint(20) DEFAULT NULL, `email` varchar(200) DEFAULT NULL, `created_by` int(11) DEFAULT NULL, `created_by_lawyer` tinyint(1) DEFAULT NULL, `progress` json DEFAULT NULL, `case_name` varchar(255) DEFAULT NULL, `sub_type` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`), KEY `idx_assigned_lawyer` (`assigned_lawyer`) ) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8

## Product Table
~~~
CREATE TABLE product_table (
    id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    stripe_lookup_key VARCHAR(100),
    price int,
    description VARCHAR(255)
);
~~~

## Order Table
~~~
CREATE TABLE order_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    case_id BIGINT,
    product_id INT,
    product_name VARCHAR(255),
    total_money INT,
    stripe_order_id VARCHAR(255),
    currency VARCHAR(10),
    payment_method_type VARCHAR(50),
    status VARCHAR(50),
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (user_id) REFERENCES user_table(id),
    FOREIGN KEY (product_id) REFERENCES product_table(id),
    FOREIGN KEY (case_id) REFERENCES application_case_table(id),
    INDEX idx_user_id (user_id), 
    UNIQUE INDEX idx_case_product (case_id, product_name)
);
~~~

## Document Table
~~~
CREATE TABLE document_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    case_id BIGINT,
    status varchar(100),
    identify varchar(50),
    type varchar(100), 
    name VARCHAR(255),
    s3_location VARCHAR(255),
    auto_generated bool,
    created_at BIGINT,
    updated_at BIGINT,
    INDEX idx_case_id (case_id),
    UNIQUE (case_id, type)
);

CREATE UNIQUE INDEX idx_unique_case_type ON document_table (case_id, type);
ALTER TABLE document_table CONVERT TO CHARACTER SET utf8;

CREATE TABLE `document_table` ( `id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) DEFAULT NULL, `case_id` bigint(20) DEFAULT NULL, `type` varchar(100) DEFAULT NULL, `file_type` varchar(255) DEFAULT NULL, `generation_type` varchar(255) DEFAULT NULL, `name` varchar(255) DEFAULT NULL, `description` varchar(255) DEFAULT NULL, `created_by` varchar(255) DEFAULT NULL, `s3_location` varchar(255) DEFAULT NULL, `created_at` bigint(20) DEFAULT NULL, `updated_at` bigint(20) DEFAULT NULL, `status` varchar(100) DEFAULT NULL, `info` json DEFAULT NULL, `identify` varchar(50) DEFAULT NULL, `auto_generated` tinyint(1) DEFAULT '0', `manual_overridden` tinyint(1) DEFAULT NULL, PRIMARY KEY (`id`), KEY `idx_composite_case_type_identify` (`case_id`,`type`,`identify`) ) ENGINE=InnoDB AUTO_INCREMENT=930 DEFAULT CHARSET=utf8

~~~


## Form Generation Task Table
~~~
CREATE TABLE form_generation_task_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    case_id BIGINT, 
    lawyer_id INT,
    user_id INT, 
    document_id BIGINT,
    form_name VARCHAR(100), 
    metadata JSON,
    status VARCHAR(100),
    s3_location VARCHAR(100),
    created_at BIGINT,
    updated_at BIGINT,
    INDEX idx_case_id (case_id)
);

~~~
CREATE TABLE `form_generation_task_table` ( `id` bigint(20) NOT NULL AUTO_INCREMENT, `case_id` bigint(20) DEFAULT NULL, `lawyer_id` int(11) DEFAULT NULL, `user_id` int(11) DEFAULT NULL, `document_id` bigint(20) DEFAULT NULL, `form_name` varchar(100) DEFAULT NULL, `type` varchar(255) DEFAULT NULL, `status` varchar(100) DEFAULT NULL, `s3_location` varchar(200) DEFAULT NULL, `created_at` bigint(20) DEFAULT NULL, `updated_at` bigint(20) DEFAULT NULL, `metadata` json DEFAULT NULL, PRIMARY KEY (`id`), KEY `idx_case_id` (`case_id`) ) ENGINE=InnoDB AUTO_INCREMENT=3585 DEFAULT CHARSET=latin1

## Lawyer table
~~~
CREATE TABLE lawyer_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    cognito_id VARCHAR(255),
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    middle_name VARCHAR(100),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    specialization VARCHAR(255),
    law_firm VARCHAR(255),
    profile JSON,
    experience_years INT,
    status INT,
    priority INT,
    occupied_capability INT,
    max_capacity INT,
    created_at BIGINT,
    updated_at BIGINT
);
INSERT INTO lawyer_table (username, first_name, last_name, email, phone_number, specialization, law_firm, profile, experience_years, status, priority, occupied_capability, max_capacity, created_at)
VALUES ('liqin@concordsage.com', 'qin', 'li', 'liqin@concordsage.com', '6197326858', 'Immigration Law', 'Concord Sage PC', '{"basicInfo":{"firstName":"Qin","streetNumberAndName":"1360 VALLEY VISTA DR","aptCheckbox":false,"steCheckbox":true,"flrCheckbox":false,"aptSteFlrNumber":"209","country":"USA","daytimeTelephoneNumber":"6197326858","emailAddress":"liqin@concordsage.com"},"eligibility":{"eligibleAttorneyCheckbox":true,"licensingAuthority":"California Supreme Court","barNumber":"349218","amCheckbox":true,"nameofLawFirm":"Concord Sage PC","accreditedRepresentativeCheckbox":false}}',, 5, 0, 0, 0, 5, UNIX_TIMESTAMP());

CREATE TABLE `lawyer_table` ( `id` int(11) NOT NULL AUTO_INCREMENT, `username` varchar(255) DEFAULT NULL, `first_name` varchar(100) DEFAULT NULL, `last_name` varchar(100) DEFAULT NULL, `middle_name` varchar(100) DEFAULT NULL, `email` varchar(255) DEFAULT NULL, `phone_number` varchar(20) DEFAULT NULL, `specialization` varchar(255) DEFAULT NULL, `law_firm` varchar(255) DEFAULT NULL, `profile` json DEFAULT NULL, `experience_years` int(11) DEFAULT NULL, `status` int(11) DEFAULT NULL, `priority` int(11) DEFAULT NULL, `occupied_capability` int(11) DEFAULT NULL, `max_capacity` int(11) DEFAULT NULL, `created_at` bigint(20) DEFAULT NULL, `updated_at` bigint(20) DEFAULT NULL, `cognito_username` varchar(225) DEFAULT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1


~~~~

## Action item table
~~~
CREATE TABLE action_item_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    case_id BIGINT,
    lawyer_id INT,
    description TEXT,
    status VARCHAR(50),
    due_date BIGINT,
    created_by INT,
    assigned_to INT,
    created_at BIGINT,
    updated_at BIGINT,
    completed_at BIGINT,
    FOREIGN KEY (user_id) REFERENCES user_table(id),
    FOREIGN KEY (case_id) REFERENCES application_case_table(id),
    FOREIGN KEY (lawyer_id) REFERENCES lawyer_table(id),
    FOREIGN KEY (created_by) REFERENCES user_table(id),
    FOREIGN KEY (assigned_to) REFERENCES user_table(id)
);
~~~


## Notification Table (TBD)
~~~
    CREATE TABLE task_table (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        case_id BIGINT,  # index
        user_id INT, 
        status VARCHAR(100),
        description VARCHAR(1250),
        due_at BIGINT,
        completed_at BIGINT,
        created_at BIGINT,
        updated_at BIGINT,
        INDEX idx_case_id (case_id)
    );
~~~

~~~
Affirmative：最终merge成一个pdf
1，cover letter
2，g28
3，i589 main form
4，i589 supplement form
5，ps chinese
6, ps english
7，i94
8，supporting documents
9，certificate of translation for ps
10，（if married）marriage certificate + cot

Defensive：最终merge成4个pdf
第一个pdf：
1，i589 cover letter
2，i589 main form
3，i589 supplement form
4，eoir28
5，proof of service
第二个pdf：
1，ps cover letter
2，ps chinese
3,  ps english
4,  certificate of translation for ps
5，ps proof of service
第三个pdf：
1，written pleading cover letter
2，written pleading
3，written pleading proof of service
第四个pdf：
1，supporting documents cover letter
2，supporting documents +（if married）marriage certificate + cot
3，supporting documents proof of service
~~~