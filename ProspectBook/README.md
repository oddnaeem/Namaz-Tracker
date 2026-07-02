# Prospect Book (প্রসপেক্ট খাতা)

কোম্পানি প্রসপেক্টিং ট্র্যাকার — Kotlin + Jetpack Compose + Room দিয়ে বানানো নেটিভ Android অ্যাপ।
কোম্পানির নাম, তাদের সমস্যা/সুযোগ, যোগাযোগের তথ্য এবং স্ট্যাটাস (নজরে আছে → যোগাযোগ হয়েছে → আলোচনা চলছে → সম্পন্ন) ফোনেই সেভ রাখে, ইন্টারনেট ছাড়াই কাজ করে।

## প্রথমবার লোকালি সেটআপ (Android Studio)

1. [Android Studio](https://developer.android.com/studio) ইনস্টল করো (এতে Android SDK ও Gradle wrapper জেনারেট করার সুবিধা থাকে)
2. এই ফোল্ডারটা **Open** করো Android Studio দিয়ে — "Open an Existing Project"
3. প্রথমবার খুললে Android Studio নিজে থেকেই Gradle wrapper ফাইল (`gradlew`, `gradlew.bat`, `gradle/wrapper/`) জেনারেট করে দেবে এবং Sync করবে
4. এমুলেটর চালু করো বা ফোন USB দিয়ে কানেক্ট করো, তারপর ▶️ Run চাপো

## GitHub-এ পুশ করা

```bash
cd ProspectBook
git init
git add .
git commit -m "Initial commit: Prospect Book app"
git branch -M main
git remote add origin https://github.com/<তোমার-ইউজারনেম>/prospect-book.git
git push -u origin main
```

`.github/workflows/build.yml` ফাইলটা push করার সাথে সাথেই **GitHub Actions** অটোমেটিক ট্রিগার হবে এবং APK বিল্ড করবে।

## APK ডাউনলোড করা

1. GitHub রিপোর অ্যাক্ট্রনস ট্যাবে যাও (Actions)
2. সর্বশেষ successful run-এ ক্লিক করো
3. নিচে **Artifacts** সেকশনে `prospect-book-debug-apk` পাবে — ডাউনলোড করে জিপ থেকে বের করলে `app-debug.apk` পাবে
4. এই APK ফোনে পাঠিয়ে ইনস্টল করো (Unknown sources এনাবল করতে হতে পারে)

> **নোট:** `assembleRelease` দিয়ে যে APK বানানো হয় সেটা **unsigned** — Play Store-এ পাবলিশ করতে চাইলে সেটা সাইন করতে হবে একটা কিস্টোর (keystore) দিয়ে। শুধু নিজের ফোনে ইনস্টল করার জন্য debug APK-ই যথেষ্ট।

## প্রজেক্ট স্ট্রাকচার

```
app/src/main/java/com/prospectbook/app/
├── MainActivity.kt          — এন্ট্রি পয়েন্ট
├── data/
│   ├── Company.kt           — Entity + Status enum
│   ├── CompanyDao.kt        — ডেটাবেস কোয়েরি
│   ├── AppDatabase.kt       — Room ডেটাবেস সেটআপ
│   └── Converters.kt        — enum ↔ string কনভার্টার
└── ui/
    ├── CompanyViewModel.kt  — UI ও ডেটাবেসের মাঝে সংযোগ
    ├── ProspectBookScreen.kt— লিস্ট, সার্চ, ফিল্টার
    ├── CompanyFormSheet.kt  — এন্ট্রি যোগ/এডিট ফর্ম
    └── theme/Theme.kt       — কালার প্যালেট
```

## ভবিষ্যতে যোগ করা যায়

- APK সাইনিং সেটআপ করে সরাসরি GitHub Releases-এ প্রকাশ করা
- ডেটা এক্সপোর্ট (CSV/Excel) ফিচার
- ক্লাউড ব্যাকআপ (Firebase বা নিজের ব্যাকএন্ড দিয়ে)
