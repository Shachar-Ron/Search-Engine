קובץ README

Information Retrieval project - development of a Search Engine using Singleton and factory DB.

###הערות חשובות:
###-1.על מנת שהתוכנית ירוץ יש לוודא שהקובץ של ה-stopWords ממוקם בתוך תיקיית הcorpus ונמצא תחת השם "  stop_words.txt 05" 
###-2. הקובץ word2vec.c.output.model צריך להיות באותה תקייה עם הקובץ BAT
###-3. הקובץ שנשמר לפלט לTRECEVAL זה result ללא שימוש בstemming וresultsstem עם שימוש בstemming. 
                     

Corpus path:
כפתור Browse:
הכנסה של path עבור תייקית ה-corpus המכילה את התיקיות והמסמכים והקובץ stop_words.txt 05
Indexer path:
כפתור Browse:
הכנסה של path עבור התיקייה שבה ישמרו כל קבצי ה-output.

כפתור stemming:
בחירת ביצוע הparser עם/בלי stemming

כפתור Run Corpus:
בעת לחיצה על הכפתור יבנה המילון וה-posting File.

כפתור reset:
מוחק את כל הקבצים שקיימים בתיקיה של הindexer ומרוקן את כל מבני הנתונים בזיכרון.

כפתור show Dictionary:
אחרי הרצת Run Corpus לחיצה על כפתור תראה את המילון באופן ויזואלי.

כפתור Load Dictionary:
מעלה את המילון מהדיסק ל-RAM.

לפני הרצת כל השלבים הבאים: 
יש להזין את ה- corpusPath, indexerPath ולעשות Load למילון בהנחה שלא עשינו run corpus.

Enter query:
כפתור search:
מזינים שאילתה ולוחצים על הכפתור. בסיום ההרצה נקבל באופן ויזאולי טבלה של 50 המסמכים הרלוונטים ביותר.
בהצגת הטבלה ניתן יהיה ללחוץ עבור כל מסמך שאוחזר על כפתור EnterEntities להחזרת 5 הישויות הלרוונטיות ביותר עבור 
המסמך הרלוונטי.

Query path:
כפתור Browse&Search: מזינים כתובת של קובץ quires שברצוננו לאחזר.

save result:
כפתור  save the result- checkBox: נלחץ אם ברצננו לשמור את הפלט בפורמט treceval.

כפתור save: נכניס מעל כפתור זה את הנתיב בו אנחנו רוצים לשמור את קובץ הtxt של הפלט ונלחץ על הכפתור.

כפתור checkBox- use oflline semantic: ביצוע אחזור בעזרת מודל סמנטי ללא חיבור לאינטרנט.
כפתור checkBox- use onlline semantic: ביצוע אחזור בעזרת מודל סמנטי עם חיבור לאינטרנט.


