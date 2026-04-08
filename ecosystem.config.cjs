module.exports = {
  apps: [
    {
      name: 'fireweb',
      script: 'java',
      args: '-jar core/build/libs/fireweb-1.0.0.jar --server.port=3000',
      cwd: '/home/user/webapp',
      env: {
        JAVA_HOME: '/usr/lib/jvm/java-17-openjdk-amd64',
        DB_HOST: 'localhost',
        DB_PORT: '3306',
        DB_NAME: 'platform_db',
        DB_USERNAME: 'platform_user',
        DB_PASSWORD: 'Kleannara12#',
        JWT_SECRET: 'fireweb-secret-key-for-jwt-authentication-2024-minimum-32-chars',
        UPLOAD_BASE_PATH: '/data/upload/module_fire'
      },
      watch: false,
      instances: 1,
      exec_mode: 'fork',
      max_memory_restart: '512M'
    }
  ]
}
