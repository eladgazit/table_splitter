# table_splitter
splits a table of multiple events into sub tables per event with the same schema

Give [seed](https://github.com/eladgazit/table_splitter/blob/master/src/main/resources/seed) data

Expected output (from log)

22/02/19 10:51:04 INFO TableSplitter: show tables in mixedtiles
+----------+----------------------+-----------+
|namespace |tableName             |isTemporary|
+----------+----------------------+-----------+
|mixedtiles|contact_support       |false      |
|mixedtiles|event_name_with_dashes|false      |
|mixedtiles|event_name_with_spaces|false      |
|mixedtiles|order_completed       |false      |
|mixedtiles|signup                |false      |
+----------+----------------------+-----------+

22/02/19 10:51:04 INFO TableSplitter: select * from mixedtiles.signup
+----------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------+------------------------------------+
|event_name|event_time             |properties|session_id                          |user_agent                                                                  |uuid                                |
+----------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------+------------------------------------+
|signup    |2021-01-01 02:00:00.000|json      |8d2f6b98-6308-40a8-9fa5-96870288102f|Mozilla/5.0 (Windows NT 6.2; en-US; rv:1.9.0.20) Gecko/20200217 Firefox/37.0|83f69e5a-bd88-4dd1-bb0a-031d2689fda2|
+----------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------+------------------------------------+

22/02/19 10:51:04 INFO TableSplitter: select * from mixedtiles.contact_support
+---------------+-----------------------+----------+------------------------------------+------------------------------------------------------------------------+------------------------------------+
|event_name     |event_time             |properties|session_id                          |user_agent                                                              |uuid                                |
+---------------+-----------------------+----------+------------------------------------+------------------------------------------------------------------------+------------------------------------+
|contact_support|2021-02-01 03:00:00.000|json      |78b39b12-2496-4874-bbdd-d0092d52c010|Mozilla/5.0 (compatible; MSIE 5.0; Windows 98; Win 9x 4.90; Trident/3.0)|b4406077-e781-436e-94a2-f426742e0877|
+---------------+-----------------------+----------+------------------------------------+------------------------------------------------------------------------+------------------------------------+

22/02/19 10:51:04 INFO TableSplitter: select * from mixedtiles.event_name_with_spaces
+----------------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+
|event_name            |event_time             |properties|session_id                          |user_agent                                                                                                            |uuid                                |
+----------------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+
|event name with spaces|2022-01-01 04:00:00.000|json      |b82frta7-3a69-4106-b506-827c217ff1a1|Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_6) AppleWebKit/5311 (KHTML, like Gecko) Chrome/39.0.816.0 Mobile Safari/5311|eeeb1dcb-0844-4920-bbd4-3a67j4863f9d|
+----------------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+

22/02/19 10:51:04 INFO TableSplitter: select * from mixedtiles.order_completed
+---------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+
|event_name     |event_time             |properties|session_id                          |user_agent                                                                                                            |uuid                                |
+---------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+
|order_completed|2021-01-01 01:00:00.000|json      |ea56ff1d-1cbd-4678-a06b-4ee7fff2d171|Mozilla/5.0 (Windows 95) AppleWebKit/5360 (KHTML, like Gecko) Chrome/36.0.828.0 Mobile Safari/5360                    |898b2e7e-9059-457e-9264-fe7a8fccaafc|
|order_completed|2022-01-01 04:00:00.000|json      |b826d4a7-3a69-4106-b506-827c217ff1a1|Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_6) AppleWebKit/5311 (KHTML, like Gecko) Chrome/39.0.816.0 Mobile Safari/5311|eeeb1dcb-0844-4920-bbd4-3acee4863f9d|
+---------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+

22/02/19 10:51:05 INFO TableSplitter: select * from mixedtiles.event_name_with_dashes
+----------------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+
|event_name            |event_time             |properties|session_id                          |user_agent                                                                                                            |uuid                                |
+----------------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+
|event-name-with-dashes|2022-01-01 04:00:00.000|json      |b82ftyt7-3a69-4106-b506-827c217ff1a1|Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_6) AppleWebKit/5311 (KHTML, like Gecko) Chrome/39.0.816.0 Mobile Safari/5311|eefgddcb-0844-4920-bbd4-3a67j4863f9d|
+----------------------+-----------------------+----------+------------------------------------+----------------------------------------------------------------------------------------------------------------------+------------------------------------+

