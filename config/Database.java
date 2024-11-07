import java.util.Map;

public class DatabaseConfig {

    // Default database connection name
    public static final String DEFAULT_CONNECTION = System.getenv().getOrDefault("DB_CONNECTION", "pgsql");

    // SQLite Configuration
    public static class SQLite {
        public static final String DRIVER = "sqlite";
        public static final String URL = System.getenv("DB_URL");
        public static final String DATABASE = System.getenv().getOrDefault("DB_DATABASE", "database.sqlite");
        public static final String PREFIX = "";
        public static final boolean FOREIGN_KEY_CONSTRAINTS = Boolean.parseBoolean(System.getenv().getOrDefault("DB_FOREIGN_KEYS", "true"));
    }

    // MySQL Configuration
    public static class MySQL {
        public static final String DRIVER = "mysql";
        public static final String URL = System.getenv("DB_URL");
        public static final String HOST = System.getenv().getOrDefault("DB_HOST", "127.0.0.1");
        public static final String PORT = System.getenv().getOrDefault("DB_PORT", "3306");
        public static final String DATABASE = System.getenv().getOrDefault("DB_DATABASE", "laravel");
        public static final String USERNAME = System.getenv().getOrDefault("DB_USERNAME", "root");
        public static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");
        public static final String UNIX_SOCKET = System.getenv().getOrDefault("DB_SOCKET", "");
        public static final String CHARSET = System.getenv().getOrDefault("DB_CHARSET", "utf8mb4");
        public static final String COLLATION = System.getenv().getOrDefault("DB_COLLATION", "utf8mb4_unicode_ci");
        public static final boolean STRICT = true;
        public static final Map<String, String> OPTIONS = (System.getenv().containsKey("MYSQL_ATTR_SSL_CA")) ? Map.of(
                "MYSQL_ATTR_SSL_CA", System.getenv("MYSQL_ATTR_SSL_CA")) : Map.of();
    }

    // MariaDB Configuration
    public static class MariaDB {
        public static final String DRIVER = "mariadb";
        public static final String URL = System.getenv("DB_URL");
        public static final String HOST = System.getenv().getOrDefault("DB_HOST", "127.0.0.1");
        public static final String PORT = System.getenv().getOrDefault("DB_PORT", "3306");
        public static final String DATABASE = System.getenv().getOrDefault("DB_DATABASE", "laravel");
        public static final String USERNAME = System.getenv().getOrDefault("DB_USERNAME", "root");
        public static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");
        public static final String UNIX_SOCKET = System.getenv().getOrDefault("DB_SOCKET", "");
        public static final String CHARSET = System.getenv().getOrDefault("DB_CHARSET", "utf8mb4");
        public static final String COLLATION = System.getenv().getOrDefault("DB_COLLATION", "utf8mb4_unicode_ci");
        public static final boolean STRICT = true;
    }

    // PostgreSQL Configuration
    public static class PostgreSQL {
        public static final String DRIVER = "pgsql";
        public static final String URL = System.getenv("DB_URL");
        public static final String HOST = System.getenv().getOrDefault("DB_HOST", "127.0.0.1");
        public static final String PORT = System.getenv().getOrDefault("DB_PORT", "5432");
        public static final String DATABASE = System.getenv().getOrDefault("DB_DATABASE", "laravel");
        public static final String USERNAME = System.getenv().getOrDefault("DB_USERNAME", "root");
        public static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");
        public static final String CHARSET = System.getenv().getOrDefault("DB_CHARSET", "utf8");
        public static final String PREFIX = "";
        public static final boolean PREFIX_INDEXES = true;
        public static final String SEARCH_PATH = "public";
        public static final String SSL_MODE = "prefer";
    }

    // SQL Server Configuration
    public static class SQLServer {
        public static final String DRIVER = "sqlsrv";
        public static final String URL = System.getenv("DB_URL");
        public static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
        public static final String PORT = System.getenv().getOrDefault("DB_PORT", "1433");
        public static final String DATABASE = System.getenv().getOrDefault("DB_DATABASE", "laravel");
        public static final String USERNAME = System.getenv().getOrDefault("DB_USERNAME", "root");
        public static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");
        public static final String CHARSET = System.getenv().getOrDefault("DB_CHARSET", "utf8");
        public static final String PREFIX = "";
        public static final boolean PREFIX_INDEXES = true;
    }

    // Migration Table Configuration
    public static class Migration {
        public static final String TABLE = "migrations";
        public static final boolean UPDATE_DATE_ON_PUBLISH = true;
    }

    // Redis Configuration
    public static class Redis {
        public static final String CLIENT = System.getenv().getOrDefault("REDIS_CLIENT", "phpredis");
        public static final String CLUSTER = System.getenv().getOrDefault("REDIS_CLUSTER", "redis");
        public static final String PREFIX = System.getenv().getOrDefault("REDIS_PREFIX", System.getenv().getOrDefault("APP_NAME", "laravel").replaceAll(" ", "_") + "_database_");
        
        public static class Default {
            public static final String URL = System.getenv("REDIS_URL");
            public static final String HOST = System.getenv().getOrDefault("REDIS_HOST", "127.0.0.1");
            public static final String USERNAME = System.getenv("REDIS_USERNAME");
            public static final String PASSWORD = System.getenv("REDIS_PASSWORD");
            public static final String PORT = System.getenv().getOrDefault("REDIS_PORT", "6379");
            public static final String DATABASE = System.getenv().getOrDefault("REDIS_DB", "0");
        }

        public static class Cache {
            public static final String URL = System.getenv("REDIS_URL");
            public static final String HOST = System.getenv().getOrDefault("REDIS_HOST", "127.0.0.1");
            public static final String USERNAME = System.getenv("REDIS_USERNAME");
            public static final String PASSWORD = System.getenv("REDIS_PASSWORD");
            public static final String PORT = System.getenv().getOrDefault("REDIS_PORT", "6379");
            public static final String DATABASE = System.getenv().getOrDefault("REDIS_CACHE_DB", "1");
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        System.out.println("Default DB Connection: " + DEFAULT_CONNECTION);
        System.out.println("SQLite Database: " + SQLite.DATABASE);
        System.out.println("MySQL Host: " + MySQL.HOST);
        System.out.println("PostgreSQL Charset: " + PostgreSQL.CHARSET);
    }
}
