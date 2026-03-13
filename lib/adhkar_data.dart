
class Dhikr {
  final String title;
  final String arabic;
  final String transliteration;
  final String meaning;

  Dhikr({
    required this.title,
    required this.arabic,
    required this.transliteration,
    required this.meaning,
  });
}

final List<Dhikr> adhkarList = [
  Dhikr(
    title: 'Subhanallah',
    arabic: 'سُبْحَانَ ٱللهِ',
    transliteration: 'Subḥāna Allāh',
    meaning: 'Glory be to Allah',
  ),
  Dhikr(
    title: 'Alhamdulillah',
    arabic: 'ٱلْحَمْدُ لِلَّٰهِ',
    transliteration: 'Al-ḥamdu lillāh',
    meaning: 'Praise be to Allah',
  ),
  Dhikr(
    title: 'Allahu Akbar',
    arabic: 'ٱللهُ أَكْبَرُ',
    transliteration: 'Allāhu Akbar',
    meaning: 'Allah is the greatest',
  ),
  Dhikr(
    title: 'La ilaha illallah',
    arabic: 'لَا إِلَٰهَ إِلَّا ٱللهُ',
    transliteration: 'Lā ilāha illā Allāh',
    meaning: 'There is no deity except Allah',
  ),
  Dhikr(
    title: 'Astaghfirullah',
    arabic: 'أَسْتَغْفِرُ ٱللهَ',
    transliteration: 'Astaghfiru Allāh',
    meaning: 'I seek forgiveness from Allah',
  ),
];
