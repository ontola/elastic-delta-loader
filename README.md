# Elastic Delta Loader

Fills the elastic endpoint for [Open Raadsinformatie](https://github.com/openstate/open-raadsinformatie/), available at
`https://api.openraadsinformatie.nl/v0/elastic`. There are other APIs available for this dataset as well
([docs](https://docs.openraadsinformatie.nl)).

This program listens to a a kafka topic and converts [incoming events](https://github.com/ontola/linked-delta) into a
searchable elastic endpoint.

## Licence
Elastic Delta Loader
Copyright (C) 2019, Argu BV

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
