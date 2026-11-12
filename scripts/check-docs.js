const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');
const root = path.resolve(__dirname, '..');
for (const name of ['AGENTS.md', 'DESIGN.md', 'README.md', 'SYSTEM_DOCUMENTATION.md', 'docs/LEARNING_GUIDE.md', 'docs/StudentExam44844Learning.pdf']) {
  assert.ok(fs.existsSync(path.join(root, name)), `Missing ${name}`);
}
const docs = fs.readFileSync(path.join(root, 'SYSTEM_DOCUMENTATION.md'), 'utf8');
const version = fs.readFileSync(path.join(root, 'app/build.gradle'), 'utf8').match(/versionName '([^']+)'/)[1];
assert.ok(docs.includes(`Phiên bản: ${version}`));
assert.ok(docs.includes('<!-- docs-check: exam44844-2026-11-10 -->'));
console.log('docs-check passed: exam 44844, version ' + version);
