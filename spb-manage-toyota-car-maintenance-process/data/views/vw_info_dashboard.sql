CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_info_dashboard` AS
    SELECT 
        (SELECT 
                COUNT(`cus`.`id`)
            FROM
                `customers` `cus`
            WHERE
                (`cus`.`deleted` = FALSE)) AS `quantityCustomer`,
        (SELECT 
                COUNT(`cars`.`id`)
            FROM
                `cars`
            WHERE
                (`cars`.`deleted` = FALSE)) AS `quantityCar`,
        (SELECT 
                COUNT(`staffs`.`id`)
            FROM
                `staffs`
            WHERE
                (`staffs`.`deleted` = FALSE)) AS `quantityStaff`,
        (SELECT 
                COUNT(`bs`.`id`)
            FROM
                `bill_service` `bs`
            WHERE
                (`bs`.`deleted` = FALSE)) AS `quantityBillService`