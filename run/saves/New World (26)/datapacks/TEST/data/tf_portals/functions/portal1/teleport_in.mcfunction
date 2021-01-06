tp @s ^ ^-1 ^ ~ ~
tag @s add p1_teleported

execute at @e[tag=portal1,distance=0..20] run tp @s ~ ~14 ~ ~ ~

execute unless entity @e[tag=portal1,distance=0..20] run fill ^1.5 ^2 ^ ^-1.5 ^-2 ^ minecraft:diamond_block
execute unless entity @e[tag=portal1,distance=0..20] run fill ^0.5 ^1 ^ ^-0.5 ^-1 ^ air
execute unless entity @e[tag=portal1,distance=0..20] run summon minecraft:armor_stand ^ ^ ^ {Invisible:1b,ArmorItems:[{},{},{},{id:"minecraft:nether_brick",Count:1b,tag:{CustomModelData:919190}}],Small:1b,NoGravity:1b,Invulnerable:1b,Tags:["portal1","forced"]}

execute as @e[tag=forced,distance=0..20] run tp @s ^ ^ ^ ~ ~
execute as @e[tag=forced,distance=0..20] run tag @s remove forced
execute at @e[tag=portal1,distance=0..20] run tp @s ^ ^-1 ^ ~ ~

playsound minecraft:block.portal.travel block @a[distance=0..15] ~ ~ ~ 0.5 2 0.1
playsound minecraft:block.water.ambient block @a[distance=0..15] ~ ~ ~ 2 2 1
playsound minecraft:entity.generic.splash block @a[distance=0..15] ~ ~ ~ 0.5 1.2 0.1