import { spawn } from 'node:child_process';

const options = {
  stdio: 'inherit',
  shell: true
};

spawn('npm run dev:run', options);

process.on('SIGINT', () => {
  cleanup();
});

function cleanup() {
  process.stdout.write('\n\n Executando o postdev...');

  const postDev = spawn('npm run postdev', options);

  postDev.on('exit', (code) => {
    process.exit(code ?? 0);
  });
}
