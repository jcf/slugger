(ns slugger.core-test
  (:use clojure.test
        slugger.core))


(deftest slug-tests
  (is (= (slug "learn how to say 你好")  "learn-how-to-say-ni-hao")))

(deftest unidecode-test
  (is (= (unidecode "Brændgaard") "Braendgaard"))

  ;; The following tests were converted from Java from Junidecoder
  (is (= (unidecode "\u00C6neid") "AEneid"))
  (is (= (unidecode "\u00e9tude") "etude"))
  ; Chinese
  (is (= (unidecode "\u5317\u4eb0") "Bei Jing "))
  ; Canadian syllabics
  (is (= (unidecode "\u1515\u14c7\u14c7") "shanana"))
  ; Cherokee
  (is (= (unidecode "\u13d4\u13b5\u13c6") "taliqua"))
  ; Syriac
  (is (= (unidecode "\u0726\u071b\u073d\u0710\u073a") "ptu'i"))
  ;Devangari
  (is (= (unidecode "\u0905\u092d\u093f\u091c\u0940\u0924") "abhijiit"))
  ; Bengali
  (is (= (unidecode "\u0985\u09ad\u09bf\u099c\u09c0\u09a4") "abhijiit"))
  ;Malayalaam
  (is (= (unidecode "\u0d05\u0d2d\u0d3f\u0d1c\u0d40\u0d24") "abhijiit"))
  
  ;; Malayalaam. The correct transliteration is "malayaalam" but
  ;; since junidecode is context insentitive this is the best we can
  ;; do.
  (is (= (unidecode "\u0d2e\u0d32\u0d2f\u0d3e\u0d32\u0d2e\u0d4d") "mlyaalm"))
  ; Japanese
  (is (= (unidecode "\u3052\u3093\u307e\u3044\u8336") "genmaiCha "))

  ; The following tests were taken from here:
  ; https://github.com/rsl/stringex/blob/master/test/unidecoder_test.rb

  ; Which took it from here originally
  ; http://www.columbia.edu/kermit/utf8.html
  (let [ dont-convert [ "Vitrum edere possum; mihi non nocet." ; Latin
                        "Je puis mangier del voirre. Ne me nuit." ; Old French
                        "Kristala jan dezaket, ez dit minik ematen." ; Basque
                        "Kaya kong kumain nang bubog at hindi ako masaktan." ; Tagalog
                        "Ich kann Glas essen, ohne mir weh zu tun." ; German
                        "I can eat glass and it doesn't hurt me."]] ; English
      (doseq [s dont-convert]
        (is (= (unidecode s) s))
        ))
  
  (let [convert { "Je peux manger du verre, ça ne me fait pas de mal."  ; French
                    "Je peux manger du verre, ca ne me fait pas de mal."
                  "Pot să mănânc sticlă și ea nu mă rănește."  ; Romanian
                    "Pot sa mananc sticla si ea nu ma raneste."
                  "Ég get etið gler án þess að meiða mig."  ; Icelandic
                    "Eg get etid gler an thess ad meida mig."
                  "Unë mund të ha qelq dhe nuk më gjen gjë."  ; Albanian
                    "Une mund te ha qelq dhe nuk me gjen gje."
                  "Mogę jeść szkło i mi nie szkodzi."  ; Polish
                    "Moge jesc szklo i mi nie szkodzi."
                  "Я могу есть стекло, оно мне не вредит."  ; Russian
                    "Ia moghu iest' stieklo, ono mnie nie vriedit."
                  "Мога да ям стъкло, то не ми вреди."  ; Bulgarian
                    "Mogha da iam stklo, to nie mi vriedi."
                  "ᛁᚳ᛫ᛗᚨᚷ᛫ᚷᛚᚨᛋ᛫ᛖᚩᛏᚪᚾ᛫ᚩᚾᛞ᛫ᚻᛁᛏ᛫ᚾᛖ᛫ᚻᛖᚪᚱᛗᛁᚪᚧ᛫ᛗᛖ᛬"  ; Anglo-Saxon
                    "ic.mag.glas.eotacn.ond.hit.ne.heacrmiacth.me:"
                  "ὕαλον ϕαγεῖν δύναμαι· τοῦτο οὔ με βλάπτει"  ; Classical Greek
                    "ualon phagein dunamai; touto ou me blaptei"
                  "मैं काँच खा सकता हूँ और मुझे उससे कोई चोट नहीं पहुंचती"  ; Hindi
                    "maiN kaaNc khaa sktaa huuN aur mujhe usse koii cott nhiiN phuNctii"
                  "من می توانم بدونِ احساس درد شيشه بخورم"  ; Persian
                    "mn my twnm bdwni Hss drd shyshh bkhwrm"
                  "أنا قادر على أكل الزجاج و هذا لا يؤلمن"  ; Arabic
                    "'n qdr `l~ 'kl lzjj w hdh l yw'lmn"
                  "אני יכול לאכול זכוכית וזה לא מזיק לי"  ; Hebrew
                    "ny ykvl lkvl zkvkyt vzh l mzyq ly"
                  "ฉันกินกระจกได้ แต่มันไม่ทำให้ฉันเจ็บ"  ; Thai
                    "chankinkracchkaid aetmanaimthamaihchanecchb"
                  "我能吞下玻璃而不伤身体。"  ; Chinese
                    "Wo Neng Tun Xia Bo Li Er Bu Shang Shen Ti . "
                  "私はガラスを食べられます。それは私を傷つけません。"  ; Japanese
                    "Si hagarasuwoShi beraremasu. sorehaSi woShang tukemasen. "}]
      (doseq [s convert]
        (is (= (unidecode (key s)) (val s))))))