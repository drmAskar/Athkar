
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:athkar/adhkar_data.dart'; // Import the new data file

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _isDarkMode = false;

  @override
  void initState() {
    super.initState();
    _loadThemePreference();
  }

  _loadThemePreference() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      _isDarkMode = prefs.getBool('isDarkMode') ?? false;
    });
  }

  _toggleTheme() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      _isDarkMode = !_isDarkMode;
      prefs.setBool('isDarkMode', _isDarkMode);
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Athkar App',
      theme: _isDarkMode ? ThemeData.dark() : ThemeData.light(),
      home: DhikrCounterPage(
        isDarkMode: _isDarkMode,
        toggleTheme: _toggleTheme,
      ),
    );
  }
}

class DhikrCounterPage extends StatefulWidget {
  final bool isDarkMode;
  final VoidCallback toggleTheme;

  const DhikrCounterPage({
    super.key,
    required this.isDarkMode,
    required this.toggleTheme,
  });

  @override
  State<DhikrCounterPage> createState() => _DhikrCounterPageState();
}

class _DhikrCounterPageState extends State<DhikrCounterPage> {
  int _counter = 0;
  int _selectedDhikrIndex = 0;

  @override
  void initState() {
    super.initState();
    _loadDhikrState();
  }

  _loadDhikrState() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      _counter = prefs.getInt('counter') ?? 0;
      _selectedDhikrIndex = prefs.getInt('selectedDhikrIndex') ?? 0;
    });
  }

  _saveDhikrState() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setInt('counter', _counter);
    prefs.setInt('selectedDhikrIndex', _selectedDhikrIndex);
  }

  _incrementCounter() {
    setState(() {
      _counter++;
    });
    _saveDhikrState();
  }

  _resetCounter() {
    setState(() {
      _counter = 0;
    });
    _saveDhikrState();
  }

  _selectDhikr(int index) {
    setState(() {
      _selectedDhikrIndex = index;
      _counter = 0; // Reset counter when dhikr changes
    });
    _saveDhikrState();
  }

  @override
  Widget build(BuildContext context) {
    Dhikr currentDhikr = adhkarList[_selectedDhikrIndex];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Athkar Counter'),
        actions: [
          IconButton(
            icon: Icon(widget.isDarkMode ? Icons.light_mode : Icons.dark_mode),
            onPressed: widget.toggleTheme,
          ),
        ],
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(
                color: Colors.blue,
              ),
              child: Text(
                'Adhkar List',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                ),
              ),
            ),
            ...adhkarList.asMap().entries.map((entry) {
              int idx = entry.key;
              Dhikr dhikr = entry.value;
              return ListTile(
                title: Text(dhikr.title),
                subtitle: Text(dhikr.arabic),
                selected: _selectedDhikrIndex == idx,
                onTap: () {
                  _selectDhikr(idx);
                  Navigator.pop(context); // Close the drawer
                },
              );
            }).toList(),
          ],
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              currentDhikr.title,
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            Text(
              currentDhikr.arabic,
              style: const TextStyle(fontSize: 48, fontFamily: 'Amiri', package: 'flutter_localizations'), // Consider custom font for Arabic
              textAlign: TextAlign.center,
            ),
            Text(
              currentDhikr.transliteration,
              style: Theme.of(context).textTheme.titleLarge,
              textAlign: TextAlign.center,
            ),
            Text(
              currentDhikr.meaning,
              style: Theme.of(context).textTheme.titleSmall,
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 50),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.displayLarge,
            ),
            const SizedBox(height: 30),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                FloatingActionButton.extended(
                  onPressed: _incrementCounter,
                  label: const Text('COUNT'),
                  icon: const Icon(Icons.add),
                ),
                const SizedBox(width: 20),
                FloatingActionButton.extended(
                  onPressed: _resetCounter,
                  label: const Text('RESET'),
                  icon: const Icon(Icons.refresh),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
