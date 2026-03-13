import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() {
  runApp(const AthkarApp());
}

class AthkarApp extends StatelessWidget {
  const AthkarApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Athkar',
      debugShowCheckedModeBanner: false,
      theme: ThemeData.light(useMaterial3: true),
      darkTheme: ThemeData.dark(useMaterial3: true),
      themeMode: ThemeMode.system,
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: [_TasbeehScreen(), const AdhkarScreen(), const SettingsScreen()][_selectedIndex],
      bottomNavigationBar: NavigationBar(
        selectedIndex: _selectedIndex,
        onDestinationSelected: (i) => setState(() => _selectedIndex = i),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.touch_app), label: 'Tasbeeh'),
          NavigationDestination(icon: Icon(Icons.list), label: 'Adhkar'),
          NavigationDestination(icon: Icon(Icons.settings), label: 'Settings'),
        ],
      ),
    );
  }
}

class _TasbeehScreen extends StatefulWidget {
  @override
  State<_TasbeehScreen> createState() => _TasbeehScreenState();
}

class _TasbeehScreenState extends State<_TasbeehScreen> {
  int _counter = 0;
  int _target = 33;
  String _currentDhikr = 'سبحان الله';

  @override
  void initState() {
    super.initState();
    _loadState();
  }

  Future<void> _loadState() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      _counter = prefs.getInt('counter') ?? 0;
      _target = prefs.getInt('target') ?? 33;
      _currentDhikr = prefs.getString('currentDhikr') ?? 'سبحان الله';
    });
  }

  Future<void> _saveState() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('counter', _counter);
    await prefs.setInt('target', _target);
    await prefs.setString('currentDhikr', _currentDhikr);
  }

  void _increment() {
    setState(() => _counter++);
    _saveState();
  }

  void _reset() {
    setState(() => _counter = 0);
    _saveState();
  }

  void _setTarget(int t) {
    setState(() => _target = t);
    _saveState();
  }

  void _setDhikr(String d) {
    setState(() => _currentDhikr = d);
    _saveState();
  }

  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;
    return Scaffold(
      appBar: AppBar(title: const Text('Tasbeeh'), centerTitle: true),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(_currentDhikr, style: const TextStyle(fontSize: 28, fontWeight: FontWeight.bold)),
            const SizedBox(height: 20),
            Text('$_counter', style: TextStyle(fontSize: 72, color: isDark ? Colors.amber : Colors.teal)),
            Text('/ $_target', style: TextStyle(fontSize: 24, color: Colors.grey)),
            const SizedBox(height: 40),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                FloatingActionButton.large(
                  heroTag: 'inc',
                  onPressed: _increment,
                  child: const Icon(Icons.add, size: 40),
                ),
                const SizedBox(width: 20),
                FloatingActionButton.large(
                  heroTag: 'reset',
                  onPressed: _reset,
                  backgroundColor: Colors.red,
                  child: const Icon(Icons.refresh, size: 40),
                ),
              ],
            ),
            const SizedBox(height: 40),
            Wrap(
              spacing: 10,
              children: [
                _chip('33', _target == 33, () => _setTarget(33)),
                _chip('99', _target == 99, () => _setTarget(99)),
                _chip('100', _target == 100, () => _setTarget(100)),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _chip(String label, bool selected, VoidCallback onTap) {
    return ActionChip(
      label: Text(label),
      backgroundColor: selected ? Colors.teal : null,
      onPressed: onTap,
    );
  }
}

class AdhkarScreen extends StatelessWidget {
  const AdhkarScreen({super.key});

  static const _adhkar = [
    {'text': 'سبحان الله', 'count': 33},
    {'text': 'الحمد لله', 'count': 33},
    {'text': 'الله أكبر', 'count': 34},
    {'text': 'لا إله إلا الله', 'count': 100},
    {'text': 'أستغفر الله', 'count': 100},
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Adhkar'), centerTitle: true),
      body: ListView.builder(
        itemCount: _adhkar.length,
        itemBuilder: (ctx, i) {
          final item = _adhkar[i];
          return ListTile(
            title: Text(item['text'] as String, style: const TextStyle(fontSize: 20)),
            subtitle: Text('${item['count']} times'),
            leading: const Icon(Icons.format_quote),
          );
        },
      ),
    );
  }
}

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({super.key});

  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  bool _isDark = false;

  @override
  void initState() {
    super.initState();
    _loadTheme();
  }

  Future<void> _loadTheme() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() => _isDark = prefs.getBool('darkMode') ?? false);
  }

  Future<void> _toggleTheme(bool val) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool('darkMode', val);
    setState(() => _isDark = val);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Settings'), centerTitle: true),
      body: ListView(
        children: [
          SwitchListTile(
            title: const Text('Dark Mode'),
            subtitle: const Text('Toggle dark/light theme'),
            value: _isDark,
            onChanged: _toggleTheme,
          ),
        ],
      ),
    );
  }
}