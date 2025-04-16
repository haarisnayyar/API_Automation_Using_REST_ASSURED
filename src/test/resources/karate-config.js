/**
 * This is the global configuration function for all Karate feature files.
 * It sets the base URL, credentials, reporting options, and environment settings.
 */
function fn() {
  // Determine active environment (e.g., qa, dev, staging)
  var env = karate.env || 'qa';
  karate.log('Running in environment:', env);

  // Enable full step logging and Allure-compatible reporting
  karate.configure('report', { showLog: true, showAllSteps: true });

  // Define global config object accessible across all feature files
  var config = {
    baseUrl: 'https://restful-booker.herokuapp.com',

    // Credentials (can be overridden via CLI: -Dauth.username=foo)
    auth: {
      username: karate.properties['auth.username'] || 'admin',
      password: karate.properties['auth.password'] || 'password123'
    }
  };

  // Log config for verification
  karate.log('Loaded config:', config);

  return config;
}
