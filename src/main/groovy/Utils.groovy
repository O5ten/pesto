class Utils {
    static boolean isWindows() {
        return System.getProperty('os.name').startsWith('Windows')
    }
    static boolean isLinux() {
        return System.getProperty('os.name').startsWith('Linux')
    }
}