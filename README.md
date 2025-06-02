#  Tuglang - Türkçeye Yakın Sözdizimli Programlama Dili

# Proje Tanımı

Tuglang, Türkçeye yakın ve anlaşılır sözdizimine sahip örnek bir yorumlayıcı programlama dilidir. Java dili kullanılarak geliştirilmiştir ve hem lexer (kelime ayrıştırıcı), hem parser (sözdizim denetleyici), hem de GUI (grafik arayüz) editörü içerir.
---

# Projenin Hedefleri

* Türkçeye yakın bir sözdizimine sahip örnek bir programlama dili tanımlamak
* Bu dile ait lexical ve syntax analiz araçlarını oluşturmak
* Java Swing ile çalışan bir editör tasarlamak
* Gerçek zamanlı sözdizimi renklendirme özelliği kazandırmak
* Derlenebilir ve .jar olarak çalıştırılabilir bir uygulama geliştirmek

---

# Kullanılan Teknolojiler

* Java SE 17+
* Swing (grafik kullanıcı arayüzü için)
* Regex (kelime ayrıştırma işlemleri için)
* GitHub ( proje yayını için)



#  Tuglang Sözdizimi

# Temel Komutlar:

* `belirle` → değişken tanımlama
* `yaz` → ekrana yazdırma
* `eger (...) { ... }` → koşullu ifade
* `dongu (...) { ... }` → döngü yapısı

### Örnek Kod:

```tuglang
belirle x = 10 + 5 ;
belirle y = x * 2 ;
belirle sonuc = (x + y) / 2 ;

yaz sonuc ;

eger (sonuc) {
    yaz x ;
    yaz y ;
}

dongu (x) {
    yaz "dongu icerisindeyiz" ;
}
```

---



## 🎨 Sözdizimi Renklendirme

Tuglang editörü içerisinde yazılan kodlar, anlık olarak aşağıdaki şekilde renklendirilir:

| Token Türü               | Renk    |
| ------------------------ | ------- |
| Anahtar kelimeler        | Mavi    |
| Değişkenler              | Mor     |
| Sayılar                  | Kırmızı |
| Operatörler              | Turuncu |
| Ayırıcılar / Parantezler | Gri     |

Renklendirme, `TuglangLexer.java` tarafından belirlenen token tiplerine göre `EditorGUI.java` dosyasında uygulanmaktadır.

---

##  Proje Yapısı

```
Tuglang/
├── src/
│   ├── TuglangLexer.java
│   ├── TuglangParser.java
│   └── EditorGUI.java
├── Tuglang_BNF.txt
├── tuglang kod örneği.txt
├── Tuglang.jar
├── README.md
└── Tuglang Proje Raporu.pdf

---
# Proje Tanıtım Videosu:
https://www.youtube.com/watch?v=AVuR98SSWYw





##  Geliştirici Bilgisi

* **Ad Soyad:** Tuğba Çevik
* **Üniversite:** Bursa Teknik Üniversitesi
* **Bölüm:** Bilgisayar Mühendisliği
* **Ders:** Programlama Dilleri




