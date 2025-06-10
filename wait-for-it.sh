#!/bin/sh

shift
cmd="$@"

echo "⏳ Waiting for MySQL at $host:3306..."
while ! nc -z mysql 3306; do
  sleep 2
done

echo "✅ MySQL is up — starting app"
exec $cmd