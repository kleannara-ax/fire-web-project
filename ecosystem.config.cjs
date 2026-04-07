module.exports = {
  apps: [
    {
      name: 'fireweb',
      script: 'java',
      args: '-jar core/build/libs/fireweb-1.0.0.jar --server.port=3000',
      cwd: '/home/user/webapp',
      env: {
        JAVA_HOME: '/usr/lib/jvm/java-17-openjdk-amd64',
        DB_USERNAME: 'fireweb',
        DB_PASSWORD: 'fireweb1234',
        JWT_SECRET: 'fireweb-secret-key-for-jwt-authentication-2024-minimum-32-chars',
        UPLOAD_BASE_PATH: '/home/user/webapp/core/uploads'
      },
      watch: false,
      instances: 1,
      exec_mode: 'fork',
      max_memory_restart: '512M'
    }
  ]
}
